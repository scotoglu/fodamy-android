package com.scoto.fodamy.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scoto.domain.models.Recipe
import com.scoto.fodamy.databinding.ItemCategoryRecipeBinding


/**
 * @author Sefa ÇOTOĞLU
 * Created 27.12.2021 at 10:19
 */
class ProfilePagingAdapter : PagingDataAdapter<Recipe, ProfilePagingAdapter.ViewHolder>(
    recipeComparator
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCategoryRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    inner class ViewHolder(private val binding: ItemCategoryRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recipe) {
            binding.recipe = item
            binding.executePendingBindings()
        }
    }

    companion object {
        val recipeComparator = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }
}
