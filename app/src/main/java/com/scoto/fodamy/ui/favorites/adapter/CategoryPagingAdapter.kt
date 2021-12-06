package com.scoto.fodamy.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.ItemCategoryBinding
import com.scoto.fodamy.ext.loadImageFromUrl
import com.scoto.fodamy.network.models.Category

class CategoryPagingAdapter() :
    PagingDataAdapter<Category, CategoryPagingAdapter.ViewHolder>(categoryComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category) {
            binding.tvCategoryTitle.text = item.name
            binding.ivCategoryImage.loadImageFromUrl(item.image?.url)
            val itemAdapter = CategoryItemAdapter(item.recipes)
            binding.rvCategoryItems.apply {
                adapter = itemAdapter
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }


        }

    }


    companion object {
        val categoryComparator = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem == newItem
        }
    }
}