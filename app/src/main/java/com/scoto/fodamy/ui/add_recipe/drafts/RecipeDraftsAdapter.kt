package com.scoto.fodamy.ui.add_recipe.drafts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scoto.domain.models.RecipeDraft
import com.scoto.fodamy.databinding.ItemDraftBinding
import com.scoto.fodamy.ext.onClick

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
class RecipeDraftsAdapter : RecyclerView.Adapter<RecipeDraftsAdapter.ViewHolder>() {

    var itemClicked: ((RecipeDraft) -> Unit)? = null
    var deleteClicked: ((RecipeDraft) -> Unit)? = null
    var editClicked: ((RecipeDraft) -> Unit)? = null
    var addPhotoClicked: ((RecipeDraft) -> Unit)? = null

    private val diffCallback = object : DiffUtil.ItemCallback<RecipeDraft>() {
        override fun areItemsTheSame(oldItem: RecipeDraft, newItem: RecipeDraft): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecipeDraft, newItem: RecipeDraft): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setData(drafts: List<RecipeDraft>) {
        differ.submitList(drafts)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeDraftsAdapter.ViewHolder {
        return ViewHolder(
            ItemDraftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeDraftsAdapter.ViewHolder, position: Int) {
        val currentDraft = differ.currentList[position]
        holder.bind(currentDraft!!)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemDraftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                draftRecipeName.onClick {
                    currentItem()?.let { item -> itemClicked?.invoke(item) }
                }
                ivDelete.onClick {
                    currentItem()?.let { item -> deleteClicked?.invoke(item) }
                }
                ivEdit.onClick {
                    currentItem()?.let { item -> editClicked?.invoke(item) }
                }
                ivAddPhoto.onClick {
                    currentItem()?.let { item -> addPhotoClicked?.invoke(item) }
                }
            }
        }

        fun bind(item: RecipeDraft) {
            binding.draftRecipeName.text = item.title
        }
    }

    fun ViewHolder.currentItem(): RecipeDraft? {
        if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
            return differ.currentList[bindingAdapterPosition]
        }
        return null
    }
}
