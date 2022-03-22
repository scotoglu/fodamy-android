package com.scoto.fodamy.ui.add_recipe.choose_photo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.scoto.fodamy.BuildConfig
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentChoosePhotoBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ui.add_recipe.choose_photo.adapter.CaptureAndPickPhotoAdapter
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Sefa ÇOTOĞLU
 * Created 17.03.2022
 */
@AndroidEntryPoint
class ChoosePhotoFragment : BaseFragment<FragmentChoosePhotoBinding, ChoosePhotoViewModel>(
    R.layout.fragment_choose_photo
) {

    private val images = mutableListOf<Uri>()
    private lateinit var captureAndPickPhotoAdapter: CaptureAndPickPhotoAdapter

    // Keep reference of captured image
    private lateinit var latestUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSelectedImageIfHas()?.let {
            images.clear()
            images.addAll(it)
            captureAndPickPhotoAdapter.setData(images)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.backTo()
                }
            }
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                viewModel.showMessage(getString(R.string.access_denied))
            } else {
                takePhoto()
            }
        }

    // takes photo, returns as uri
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestUri.let {
                    images.add(it)
                    captureAndPickPhotoAdapter.setData(images)
                }
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
                        requireActivity().contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                        images.add(uri)
                    }
                }
                captureAndPickPhotoAdapter.setData(images)
            }
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
                // Show Message why do you need this permission
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun chooseFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        galleryIntent.type = "image/*"
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        galleryLauncher.launch(galleryIntent)
    }

    // launch camera, after permission granted
    private fun takePhoto() {
        val uri = createImageUri()
        latestUri = uri
        cameraLauncher.launch(uri)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageUri(): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_", // prefix
            ".jpg", // suffix
            storageDir // directory
        )
        return FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, file)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun addItemClicks() {
        super.addItemClicks()
        captureAndPickPhotoAdapter.deleteClicked = {
            images.remove(it)
            captureAndPickPhotoAdapter.notifyDataSetChanged()
        }
    }

    override fun initViews() {
        super.initViews()
        with(CaptureAndPickPhotoAdapter()) {
            captureAndPickPhotoAdapter = this
            this.setData(images)
        }
        binding.captureAndPickRecyclerview.apply {
            setHasFixedSize(true)
            adapter = captureAndPickPhotoAdapter
        }
        binding.btnSaveDraft.onClick {
            viewModel.toPublishDraft(images)
        }
    }
}
