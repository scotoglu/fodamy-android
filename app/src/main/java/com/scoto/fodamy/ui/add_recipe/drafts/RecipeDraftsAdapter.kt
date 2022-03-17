package com.scoto.fodamy.ui.add_recipe.drafts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scoto.domain.models.RecipeDraft
import com.scoto.fodamy.databinding.ItemDraftBinding
import com.scoto.fodamy.ext.onClick

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
class RecipeDraftsAdapter : RecyclerView.Adapter<RecipeDraftsAdapter.ViewHolder>() {

    private var drafts: List<RecipeDraft>? = null
    var itemClicked: ((RecipeDraft) -> Unit)? = null
    var deleteClicked: ((RecipeDraft) -> Unit)? = null
    var editClicked: ((RecipeDraft) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(drafts: List<RecipeDraft>) {
        this.drafts = drafts
        notifyDataSetChanged()
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
        val currentDraft = drafts?.get(position)
        holder.bind(currentDraft!!)
    }

    override fun getItemCount(): Int {
        return drafts?.size ?: 0
    }

    inner class ViewHolder(private val binding: ItemDraftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                draftRecipeName.onClick {
                    if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        drafts?.get(bindingAdapterPosition)
                            ?.let { item -> itemClicked?.invoke(item) }
                    }
                }
                ivDelete.onClick {
                    if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        drafts?.get(bindingAdapterPosition)
                            ?.let { item -> deleteClicked?.invoke(item) }
                    }
                }
                ivEdit.onClick {
                    if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        drafts?.get(bindingAdapterPosition)
                            ?.let { item -> editClicked?.invoke(item) }
                    }
                }
            }
        }

        fun bind(item: RecipeDraft) {
            binding.draftRecipeName.text = item.title
        }
    }
}
