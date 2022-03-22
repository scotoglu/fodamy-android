package com.scoto.fodamy.ui.add_recipe.choose_photo.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.scoto.fodamy.databinding.ItemPhotoViewBinding
import com.scoto.fodamy.ext.onClick

/**
 * @author Sefa ÇOTOĞLU
 * Created 21.03.2022
 */
class CaptureAndPickPhotoAdapter : RecyclerView.Adapter<CaptureAndPickPhotoAdapter.ViewHolder>() {
    private lateinit var imageUri: List<Uri>
    private var hideDelete = true
    var deleteClicked: ((Uri) -> Unit)? = null

    fun hideDelete() {
        hideDelete = false
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(imageUri: List<Uri>) {
        this.imageUri = imageUri
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPhotoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUri[position])
    }

    override fun getItemCount(): Int {
        return imageUri.size
    }

    inner class ViewHolder(
        private val binding: ItemPhotoViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivDelete.onClick {
                if (bindingAdapterPosition != NO_POSITION) {
                    val currentItem = imageUri[bindingAdapterPosition]
                    deleteClicked?.invoke(currentItem)
                }
            }
        }

        fun bind(uri: Uri) {
            binding.apply {
                ivDelete.isVisible = hideDelete
                ivPhoto.setImageURI(uri)
                executePendingBindings()
            }
        }
    }
}
