package com.tronindmitr.githubsearch.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tronindmitr.githubsearch.databinding.ListItemHistoryScreenBinding
import com.tronindmitr.githubsearch.databinding.ListItemSearchScreenBinding


class SearchScreenViewAdapter(val clickListner: RepositoryItemListener) : ListAdapter<RepositoryItem, SearchScreenViewAdapter.ViewHolder>(
    RepositoryItemDiffCallback()
) {

    override fun onCreateViewHolder(parentViewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parentViewGroup)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, clickListner)
    }

    class ViewHolder private constructor(val binding: ListItemSearchScreenBinding) : RecyclerView.ViewHolder(binding.root) {

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
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSearchScreenBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class HistoryScreenViewAdapter(val itemClickListner: RepositoryItemListener, val favorClickListener : RepositoryItemListener) : ListAdapter<RepositoryItem, HistoryScreenViewAdapter.ViewHolder>(
    RepositoryItemDiffCallback()
) {

    override fun onCreateViewHolder(parentViewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parentViewGroup)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, itemClickListner, favorClickListener)
    }

    class ViewHolder private constructor(val binding: ListItemHistoryScreenBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: RepositoryItem,
            itemClickListner: RepositoryItemListener,
            favorClickListener: RepositoryItemListener
            ) {
            binding.repositoryItem = item
            binding.itemClickListener = itemClickListner
            binding.favorClickListener = favorClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent : ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHistoryScreenBinding.inflate(layoutInflater, parent, false)
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

class RepositoryItemListener(val clickListner: (repositoryItem: RepositoryItem) -> Unit) {
    fun onClick(repositoryItem: RepositoryItem) = clickListner(repositoryItem)
}


