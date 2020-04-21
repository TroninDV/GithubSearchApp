package com.tronindmitr.githubsearch.screens.historyScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.tronindmitr.githubsearch.databinding.FragmentHistoryScreenBinding
import com.tronindmitr.githubsearch.screens.database.RepositoryDatabase
import com.tronindmitr.githubsearch.screens.util.RepositoryItemListener
import com.tronindmitr.githubsearch.screens.util.RepositoryViewAdapter

/**
 * A simple [Fragment] subclass.
 */
class HistoryScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //var binding : FragmentHistoryScreenBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_screen, container, false)
        var binding = FragmentHistoryScreenBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataSource = RepositoryDatabase.getInstance(application).repositoryDatabaseDao

        val viewModelFactory = HistoryScreenViewModelFactory(dataSource, application)

        val historyScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryScreenViewModel::class.java)

        binding.historyScreenViewModel = historyScreenViewModel

        binding.lifecycleOwner = this

        val adapter =
            RepositoryViewAdapter(
                RepositoryItemListener { repositoryItem ->
                    historyScreenViewModel.onItemBrowse(repositoryItem)

                    val openUrlIntent = Intent(Intent.ACTION_VIEW)
                    openUrlIntent.data = Uri.parse(repositoryItem.url)
                    startActivity(openUrlIntent)

                    Toast.makeText(this.context, repositoryItem.url, Toast.LENGTH_SHORT).show()
                })

        binding.recyclerListHistoryScreenFragment.adapter = adapter

        return binding.root
    }


}
