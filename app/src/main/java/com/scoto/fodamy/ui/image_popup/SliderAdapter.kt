package com.scoto.fodamy.ui.image_popup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.ItemImageSliderBinding
import com.scoto.domain.models.Image
import com.scoto.domain.models.ImageList

class SliderAdapter(private val images: ImageList) :
    RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.image = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images.images?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return images.images.size
    }
}
