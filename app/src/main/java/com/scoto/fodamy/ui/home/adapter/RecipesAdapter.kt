package com.scoto.fodamy.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scoto.domain.models.Recipe
import com.scoto.fodamy.databinding.ItemFoodCardBinding

class RecipesAdapter() : PagingDataAdapter<Recipe, RecipesAdapter.ViewHolder>(recipeComparator) {

    var onItemClicked: ((Recipe) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFoodCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((binding))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(val binding: ItemFoodCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.clContainer.setOnClickListener {

                val currentItemPosition = bindingAdapterPosition
                if (currentItemPosition != RecyclerView.NO_POSITION) {
                    val currentItem = getItem(bindingAdapterPosition)
                    currentItem?.let {
                        onItemClicked?.invoke(it)
                    }
                }
            }
        }

        fun bind(item: Recipe) {
            binding.recipe = item
            binding.executePendingBindings()
        }
    }

    companion object {
        private val recipeComparator = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }
}
