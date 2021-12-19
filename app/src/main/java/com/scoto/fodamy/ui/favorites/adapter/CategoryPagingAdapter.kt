package com.scoto.fodamy.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.ItemCategoryBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.network.models.Category
import com.scoto.fodamy.network.models.Recipe

class CategoryPagingAdapter() :
    PagingDataAdapter<Category, CategoryPagingAdapter.ViewHolder>(categoryComparator) {

    var onItemClicked: ((Category) -> Unit)? = null
    var onChildItemClicked: ((Recipe) -> Unit)? = null

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

        init {
            binding.tvSeeAll.onClick {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = getItem(position)
                    currentItem?.let {
                        onItemClicked?.invoke(it)
                    }
                }
            }
        }

        fun bind(item: Category) {
            binding.apply {
                cTitle = item.name
                cUrl = item.image?.url ?: " "
            }

            val itemAdapter = CategoryItemAdapter(item.recipes!!)

            itemAdapter.onCategoryItemClicked = {
                onChildItemClicked?.invoke(it)
            }

            binding.rvCategoryItems.apply {
                adapter = itemAdapter
                setHasFixedSize(true)
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
