package com.tronindmitr.githubsearch.screens.searchScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.tronindmitr.githubsearch.R
import com.tronindmitr.githubsearch.databinding.FragmentSearchScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class SearchScreenFragment : Fragment() {

    private val viewModel: SearchScreenViewModel by lazy {
        ViewModelProviders.of(this).get(SearchScreenViewModel::class.java)
    }

    private lateinit var binding: FragmentSearchScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentSearchScreenBinding.inflate(inflater)

        binding.lifecycleOwner = this

        //viewModel =

        binding.searchScreenViewModel = viewModel

        binding.historyButtonSearchScreenFragment.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_searchScreenFragment_to_historyScreenFragment)
        }

        binding.searchButtonSearchScreenFragment.setOnClickListener {onCLick()}

        return binding.root
    }

    private fun onCLick() {
        val string = binding.inputBarTextSearchScreenFragment.text
        if (string.isNotBlank() && string.isNotEmpty())
            viewModel.onClick(string)
        else
            Toast.makeText(this.context, "Empty string", Toast.LENGTH_SHORT).show()


    }


}
