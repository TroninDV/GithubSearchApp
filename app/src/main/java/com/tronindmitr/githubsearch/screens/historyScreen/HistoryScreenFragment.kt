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
                },
                RepositoryItemListener { it ->
                    historyScreenViewModel.onClickFav(it)
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
            R.id.sort_by_menu_history_screen -> sortByDialog()
            R.id.delete_all_history_screen -> {
                historyScreenViewModel.onClickDeleteAll()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortByDialog() {
        var defaultOption: Int = if (historyScreenViewModel.gtFiltered()) 1 else 0
        val alertDialog = AlertDialog.Builder(this.context)
            .setTitle("Sort By")
            .setSingleChoiceItems(
                arrayOf("All", "Favorite"), defaultOption
            )
            { dialog: DialogInterface?, which: Int ->
                defaultOption = which
            }
            .setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                when (defaultOption) {
                    0 -> historyScreenViewModel.onClickSortByAll()
                    1 -> historyScreenViewModel.onClickSortByFav()
                }
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int -> }

        alertDialog.create().show()
    }


}
