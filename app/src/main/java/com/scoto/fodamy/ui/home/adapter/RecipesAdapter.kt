package com.scoto.fodamy.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.ItemFoodCardBinding
import com.scoto.fodamy.network.models.Recipe

class RecipesAdapter
    : PagingDataAdapter<Recipe, RecipesAdapter.ViewHolder>(recipeComparator) {

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
                //Item to details screen
            }
        }

        fun bind(item: Recipe) {
            binding.recipe = item
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