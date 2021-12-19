package com.scoto.fodamy.ui.walkthrough.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.ItemWalkthroughBinding
import com.scoto.fodamy.ui.walkthrough.model.Walkthrough

class WalkThroughAdapter(
    private val content: List<Walkthrough>
) : RecyclerView.Adapter<WalkThroughAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWalkthroughBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            content[position]
        )
    }

    override fun getItemCount(): Int = content.size

    inner class ViewHolder(
        private val binding: ItemWalkthroughBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Walkthrough) {
            binding.apply {
                tvWalkthroughTitle.text = item.title
                tvWalkthroughDescription.text = item.description
                ivWalkthrough.setImageResource(item.image)
            }
        }
    }
}
