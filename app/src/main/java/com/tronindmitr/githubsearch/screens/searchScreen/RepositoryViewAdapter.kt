package com.tronindmitr.githubsearch.screens.searchScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tronindmitr.githubsearch.RepositoryItem
import com.tronindmitr.githubsearch.databinding.ListItemRepositoryBinding

class RepositoryViewAdapter : ListAdapter<RepositoryItem, RepositoryViewAdapter.ViewHolder>(RepositoryItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepositoryItem) {
            binding.nameText.text = item.name
            binding.dateText.text = item.date
            binding.langText.text = item.language ?: ""
            binding.descriptionText.text = item.description ?: ""
            binding.ownerText.text = item.owner.login
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent : ViewGroup): ViewHolder {
                val lauoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemRepositoryBinding.inflate(lauoutInflater, parent, false)
                return ViewHolder(binding)
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
