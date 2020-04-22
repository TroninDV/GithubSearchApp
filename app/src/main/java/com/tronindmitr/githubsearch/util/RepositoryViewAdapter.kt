package com.tronindmitr.githubsearch.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tronindmitr.githubsearch.databinding.ListItemRepositoryBinding

class RepositoryViewAdapter(val clickListner: RepositoryItemListener) : ListAdapter<RepositoryItem, RepositoryViewAdapter.ViewHolder>(
    RepositoryItemDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, clickListner)
    }

    class ViewHolder private constructor(val binding: ListItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: RepositoryItem,
            clickListner: RepositoryItemListener
        ) {
            binding.repositoryItem = item
            binding.clickListener = clickListner
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent : ViewGroup): ViewHolder {
                val lauoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemRepositoryBinding.inflate(lauoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}

class RepositoryItemDiffCallback : DiffUtil.ItemCallback<RepositoryItem>() {
    override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
        return oldItem == newItem
    }
}

class RepositoryItemListener(val clickListner: (repositoryItem: RepositoryItem) -> Unit) {
    fun onClick(repositoryItem: RepositoryItem) = clickListner(repositoryItem)
}


