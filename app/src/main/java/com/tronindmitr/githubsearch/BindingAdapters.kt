package com.tronindmitr.githubsearch

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tronindmitr.githubsearch.screens.searchScreen.RepositorySearchApiStatus
import com.tronindmitr.githubsearch.screens.searchScreen.RepositoryViewAdapter

@BindingAdapter("listData")
fun bindRecycleView(recucleView: RecyclerView, data: List<RepositoryItem>?) {
    val adapter = recucleView.adapter as RepositoryViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("repositorySearchApiErrorStatus")
fun bindStatus(statusImageView: ImageView, status: RepositorySearchApiStatus?) {
    when(status) {
        RepositorySearchApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
        }
        RepositorySearchApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        RepositorySearchApiStatus.LOADING -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("repositorySearchApiLoadingStatus")
fun BindStatus(loadingLayout: RelativeLayout, status: RepositorySearchApiStatus?) {
    when(status) {
        RepositorySearchApiStatus.LOADING -> {
            loadingLayout.visibility = View.VISIBLE
        }
        RepositorySearchApiStatus.DONE -> {
            loadingLayout.visibility = View.GONE
        }
        RepositorySearchApiStatus.ERROR -> {
            loadingLayout.visibility = View.GONE}
    }
}

