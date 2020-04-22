package com.tronindmitr.githubsearch.screens.searchScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tronindmitr.githubsearch.R
import com.tronindmitr.githubsearch.databinding.FragmentSearchScreenBinding
import com.tronindmitr.githubsearch.database.RepositoryDatabase
import com.tronindmitr.githubsearch.util.RepositoryItemListener
import com.tronindmitr.githubsearch.util.SearchScreenViewAdapter

/**
 * A simple [Fragment] subclass.
 */
class SearchScreenFragment : Fragment() {

    private lateinit var binding: FragmentSearchScreenBinding

    private lateinit var searchScreenViewModel: SearchScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application

        val dataSource = RepositoryDatabase.getInstance(application).repositoryDatabaseDao

        val viewModelFactory = SearchScreenViewModelFactory(dataSource, application)

        binding = FragmentSearchScreenBinding.inflate(inflater)

        searchScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            SearchScreenViewModel::class.java)

        binding.searchScreenViewModel = searchScreenViewModel

        binding.lifecycleOwner = this

        val adapter =
            SearchScreenViewAdapter(
                RepositoryItemListener { repositoryItem ->
                    searchScreenViewModel.onItemBrowse(repositoryItem)
                    val openUrlIntent = Intent(Intent.ACTION_VIEW)
                    openUrlIntent.data = Uri.parse(repositoryItem.url)
                    startActivity(openUrlIntent)

                    Toast.makeText(this.context, repositoryItem.url, Toast.LENGTH_SHORT).show()
                })
        binding.recyclerListSearchScreenFragment.adapter = adapter

        binding.historyButtonSearchScreenFragment.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_searchScreenFragment_to_historyScreenFragment)
        }

        binding.searchButtonSearchScreenFragment.setOnClickListener { onCLick() }

        binding.inputBarTextSearchScreenFragment.setOnEditorActionListener { textView, actionId, event ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
                onCLick()
                true
            } else false
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu_search_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||  super.onOptionsItemSelected(item)
    }

    private fun onCLick() {
        //Hiding the keyboard
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)

        val string = binding.inputBarTextSearchScreenFragment.text.toString()
        if (string.isNotBlank() && string.isNotEmpty()) {
            searchScreenViewModel.onClick(string)
        } else
            Toast.makeText(this.context, "Empty string", Toast.LENGTH_SHORT).show()
    }


}
