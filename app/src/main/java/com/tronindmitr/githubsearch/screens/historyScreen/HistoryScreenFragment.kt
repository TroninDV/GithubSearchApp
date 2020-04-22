package com.tronindmitr.githubsearch.screens.historyScreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.tronindmitr.githubsearch.R
import com.tronindmitr.githubsearch.databinding.FragmentHistoryScreenBinding
import com.tronindmitr.githubsearch.database.RepositoryDatabase
import com.tronindmitr.githubsearch.util.HistoryScreenViewAdapter
import com.tronindmitr.githubsearch.util.RepositoryItemListener
import com.tronindmitr.githubsearch.util.SearchScreenViewAdapter

/**
 * A simple [Fragment] subclass.
 */
class HistoryScreenFragment : Fragment() {

    lateinit var historyScreenViewModel : HistoryScreenViewModel
    lateinit var adapter : HistoryScreenViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //var binding : FragmentHistoryScreenBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_screen, container, false)
        var binding = FragmentHistoryScreenBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataSource = RepositoryDatabase.getInstance(application).repositoryDatabaseDao

        val viewModelFactory = HistoryScreenViewModelFactory(dataSource, application)

         historyScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryScreenViewModel::class.java)

        binding.historyScreenViewModel = historyScreenViewModel

        binding.lifecycleOwner = this

         adapter =
            HistoryScreenViewAdapter(
                RepositoryItemListener { repositoryItem ->
                    historyScreenViewModel.onItemBrowse(repositoryItem)

                    val openUrlIntent = Intent(Intent.ACTION_VIEW)
                    openUrlIntent.data = Uri.parse(repositoryItem.url)
                    startActivity(openUrlIntent)

                    Toast.makeText(this.context, repositoryItem.url, Toast.LENGTH_SHORT).show()
                },
                RepositoryItemListener { it ->
                    historyScreenViewModel.onClickFav(it)
                    Toast.makeText(this.context, "Fav " + it.id, Toast.LENGTH_SHORT).show()
                })

        binding.recyclerListHistoryScreenFragment.adapter = adapter

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu_history_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_menu_history_screen -> {
                var answer: Int = 1
                val alertDialog = AlertDialog.Builder(this.context)
                    .setTitle("Sort By")
                    .setSingleChoiceItems(arrayOf("All", "Favorite"), 1)
                        { dialog: DialogInterface?, which: Int ->
                            answer = which }
                    .setPositiveButton("OK") {dialog: DialogInterface?, which: Int ->
                        when(answer) {
                            0 -> historyScreenViewModel.isFiltered.postValue(false)
                            1 -> historyScreenViewModel.isFiltered.postValue(true)
                        }
                    }
                    .setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int -> }

                alertDialog.create().show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
