package com.tronindmitr.githubsearch.util

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tronindmitr.githubsearch.screens.searchScreen.RepositorySearchApiStatus

@BindingAdapter("listDataSearchScreen")
fun bindRecycleViewSearchScreen(recucleView: RecyclerView, data: List<RepositoryItem>?) {
    val adapter = recucleView.adapter as SearchScreenViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataHistoryScreen")
fun bindRecycleViewHistoryScreen(recucleView: RecyclerView, data: List<RepositoryItem>?) {
    val adapter = recucleView.adapter as HistoryScreenViewAdapter
    adapter.submitList(data)
}



@BindingAdapter("repositorySearchApiErrorStatus")
fun bindErrorStatus(statusImageView: ImageView, status: RepositorySearchApiStatus?) {
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
        RepositorySearchApiStatus.EMPTY ->  statusImageView.visibility = View.GONE
    }
}

@BindingAdapter("repositorySearchApiLoadingStatus")
fun bindLoadingStatus(loadingLayout: RelativeLayout, status: RepositorySearchApiStatus?) {
    when(status) {
        RepositorySearchApiStatus.LOADING -> {
            loadingLayout.visibility = View.VISIBLE
        }
        RepositorySearchApiStatus.DONE -> {
            loadingLayout.visibility = View.GONE
        }
        RepositorySearchApiStatus.ERROR -> {
            loadingLayout.visibility = View.GONE}
        RepositorySearchApiStatus.EMPTY ->  loadingLayout.visibility = View.GONE
    }
}

@BindingAdapter("repositorySearchApiEmptyStatus")
fun TextView.bindEmptyStatus(status: RepositorySearchApiStatus?) {
    when(status) {
        RepositorySearchApiStatus.LOADING -> visibility = View.GONE
        RepositorySearchApiStatus.EMPTY -> visibility = View.VISIBLE
        RepositorySearchApiStatus.ERROR -> visibility = View.GONE
        RepositorySearchApiStatus.DONE -> visibility = View.GONE
    }
}


