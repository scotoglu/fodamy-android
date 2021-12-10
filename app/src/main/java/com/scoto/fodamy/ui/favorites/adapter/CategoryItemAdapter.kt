package com.scoto.fodamy.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.ItemCategoryRecipeBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.network.models.Recipe

class CategoryItemAdapter(
    private val recipes: List<Recipe>
) : RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>() {


    var onCategoryItemClicked: ((Recipe) -> Unit)? = null


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

        init {
            binding.recipeCardView.onClick {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onCategoryItemClicked?.invoke(recipes[bindingAdapterPosition])
                }
            }
        }

        fun bind(item: Recipe) {
            binding.apply {
                recipe = item
                executePendingBindings()
            }

        }
    }
}