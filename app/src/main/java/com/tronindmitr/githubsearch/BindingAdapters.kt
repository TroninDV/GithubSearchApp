package com.tronindmitr.githubsearch

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tronindmitr.githubsearch.screens.searchScreen.RepositoryViewAdapter

@BindingAdapter("listData")
fun bindRecycleView(recucleView: RecyclerView, data: List<RepositoryItem>?) {
    val adapter = recucleView.adapter as RepositoryViewAdapter
    adapter.submitList(data)

}