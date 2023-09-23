package com.ozge.tunestore.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 15.09.2023
 * @author Özge Şahin
 */

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchAdapter.SearchListener {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private  val viewModel by viewModels<SearchViewModel>()

    private lateinit var auth: FirebaseAuth

    private val searchAdapter by lazy { SearchAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        observeData()


        with(binding) {
            rvSearchProducts.adapter = searchAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (!query.isNullOrBlank() && query.length >= 3) {
                        viewModel.getSearchProducts(query)
                    }
                    return true
                }

            })

        }


    }

    private fun observeData(){
        viewModel.searchLiveData.observe(viewLifecycleOwner){list ->
            if (list.isNullOrEmpty().not()){
                searchAdapter.submitList(list)
            }else{
                searchAdapter.submitList(list)
            }
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner){
            Snackbar.make(requireView(), "" ,1000).show()
        }
    }

    override fun onSearchItemClick(id: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }
}