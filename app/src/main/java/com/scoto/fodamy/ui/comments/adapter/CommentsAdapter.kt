package com.scoto.fodamy.ui.comments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.scoto.fodamy.databinding.UserCommentCardBinding
import com.scoto.fodamy.network.models.Comment

class CommentsAdapter : PagingDataAdapter<Comment, CommentsAdapter.ViewHolder>(commentComparator) {

    var onItemLongClicked: ((Comment) -> Unit)? = null

    inner class ViewHolder(val binding: UserCommentCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                tvComments.setOnLongClickListener {
                    if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        val currentItem = getItem(bindingAdapterPosition)
                        currentItem?.let {
                            onItemLongClicked?.invoke(it)
                        }
                    }

                    false
                }

                textView2.isVisible = false
                divider2.isVisible = false
            }
        }

        fun bind(item: Comment) {
            binding.comment = item
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserCommentCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val commentComparator = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem
        }
    }
}
