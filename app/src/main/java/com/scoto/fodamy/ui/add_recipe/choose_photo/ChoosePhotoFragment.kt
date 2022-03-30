package com.scoto.fodamy.ui.add_recipe.choose_photo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentChoosePhotoBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ext.showPermissionDialog
import com.scoto.fodamy.ui.add_recipe.choose_photo.adapter.CaptureAndPickPhotoAdapter
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author Sefa ÇOTOĞLU
 * Created 17.03.2022
 */
@AndroidEntryPoint
class ChoosePhotoFragment : BaseFragment<FragmentChoosePhotoBinding, ChoosePhotoViewModel>(
    R.layout.fragment_choose_photo
) {

    private val imagesFiles = mutableListOf<File>()
    private lateinit var captureAndPickPhotoAdapter: CaptureAndPickPhotoAdapter
    private var grantedPermissionIs = ""

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.backTo()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                viewModel.showMessageWithRes(R.string.access_denied)
            } else {
                grantedPermissionIs = if (grantedPermissionIs == Manifest.permission.CAMERA) {
                    takePhoto()
                    ""
                } else {
                    chooseFromGallery()
                    ""
                }
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val bitmap = result.data?.extras?.get(DATA) as Bitmap
                val imgFile = createTempFile()
                convertBitmapToFile(imgFile, bitmap)
                imagesFiles.add(imgFile)
                captureAndPickPhotoAdapter.setData(imagesFiles)
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val clipData = it.data?.clipData
                val itemCount = clipData?.itemCount
                if (itemCount != null) {
                    for (i in 0 until itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        imagesFiles.add(getImgFile(uri))
                    }
                }
                captureAndPickPhotoAdapter.setData(imagesFiles)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSelectedImageIfHas()?.let {
            imagesFiles.clear()
            imagesFiles.addAll(it)
            captureAndPickPhotoAdapter.setData(imagesFiles)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
    }

    private fun getImgFile(uri: Uri): File {
        val bitmap: Bitmap? = getBitmap(uri)
        val imgFile = createTempFile()
        bitmap?.let { btm ->
            convertBitmapToFile(imgFile, btm)
        }
        return imgFile
    }

    override fun registerObservables() {
        super.registerObservables()
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                ChoosePhotoFragmentEvent.OpenCamera -> checkPermissionAndGoOn(Manifest.permission.CAMERA)
                ChoosePhotoFragmentEvent.OpenGallery -> checkPermissionAndGoOn(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkPermissionAndGoOn(permission: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                if (permission == Manifest.permission.CAMERA)
                    takePhoto()
                else
                    chooseFromGallery()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                // Show message to user why you need this permission
                grantedPermissionIs = permission
                requireContext().showPermissionDialog { requestPermissionLauncher.launch(permission) }
            }
            else -> {
                grantedPermissionIs = permission
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    // opens gallery, allows multiple selection
    private fun chooseFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_OPEN_DOCUMENT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.type = "image/*"
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        galleryLauncher.launch(galleryIntent)
    }

    // launch camera, after permission granted
    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(takePictureIntent)
    }

    private fun convertBitmapToFile(destination: File, bitmap: Bitmap) {
        lifecycleScope.launch(Dispatchers.IO) {
            destination.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
            val bitmapData = bos.toByteArray()
            try {
                val fos = FileOutputStream(destination)
                fos.write(bitmapData)
                fos.flush()
                fos.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun getBitmap(uri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    requireContext().contentResolver, uri
                )
            )
        } else {
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun addItemClicks() {
        super.addItemClicks()
        captureAndPickPhotoAdapter.deleteClicked = {
            imagesFiles.remove(it)
            captureAndPickPhotoAdapter.notifyDataSetChanged()
        }
    }

    override fun initViews() {
        super.initViews()
        with(CaptureAndPickPhotoAdapter()) {
            captureAndPickPhotoAdapter = this
            this.setData(imagesFiles)
        }
        binding.captureAndPickRecyclerview.apply {
            setHasFixedSize(true)
            adapter = captureAndPickPhotoAdapter
        }
        binding.btnSaveDraft.onClick {
            viewModel.toPublishDraft(imagesFiles)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createTempFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", // prefix
            ".jpg", // suffix
            storageDir // directory
        )
    }

    //    private fun compressImage(filePath: String, targetMb: Double = 1.0) {
//        var image: Bitmap = BitmapFactory.decodeFile(filePath)
//        val exif = ExifInterface(filePath)
//        val exifOrientation: Int = exif.getAttributeInt(
//            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
//        )
//        val exifDegrees: Int = exifOrientationToDegrees(exifOrientation)
//
//        image = rotateImage(image, exifDegrees.toFloat())
//        try {
//            val file = File(filePath)
//            val length = file.length()
//            val fileSizeInKB = (length / 1024).toString().toDouble()
//            val fileSizeInMB = (fileSizeInKB / 1024).toString().toDouble()
//            var quality = 100
//            if (fileSizeInMB > targetMb) {
//                quality = ((targetMb / fileSizeInMB) * 100).toInt()
//            }
//            val fileOutputStream = FileOutputStream(filePath)
//            image.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
//            fileOutputStream.close()
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//    }
//
//    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
//        return when (exifOrientation) {
//            ExifInterface.ORIENTATION_ROTATE_90 -> {
//                90
//            }
//            ExifInterface.ORIENTATION_ROTATE_180 -> {
//                180
//            }
//            ExifInterface.ORIENTATION_ROTATE_270 -> {
//                270
//            }
//            else -> {
//                0
//            }
//        }
//    }
//
//    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
//        val matrix = Matrix()
//        matrix.postRotate(angle)
//        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
//    }
    companion object {
        private const val DATA = "data"
    }
}
