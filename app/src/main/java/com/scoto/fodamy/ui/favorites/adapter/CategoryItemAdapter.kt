package com.scoto.fodamy.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.ItemCategoryRecipeBinding
import com.scoto.fodamy.network.models.Recipe

class CategoryItemAdapter(
    private val recipes: List<Recipe>
) : RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    inner class ViewHolder(
        private val binding: ItemCategoryRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recipe) {
            binding.apply {
                recipe = item
                executePendingBindings()
            }

        }
    }
}