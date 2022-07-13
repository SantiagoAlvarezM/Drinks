package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.model.DrinksActions
import com.example.myapplication.util.throttleLatest

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DrinkAdapter
    private val viewModel: DrinkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = DrinkAdapter { drink ->
            findNavController().navigate(R.id.action_SearchFragment_to_SecondFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearch.adapter = adapter

        val request = throttleLatest<String>(API_REQUEST_DELAY, viewLifecycleOwner.lifecycleScope) {
            val query = it.trim().lowercase()
            if (query.isNotBlank()) {
                viewModel.searchDrinks(query)
            } else {
                hideLoader()
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            request(binding.edittextFirst.editableText.toString())
        }
        binding.edittextFirst.setOnEditorActionListener { v, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                showLoader()
                request(v.editableText.toString())
                true
            } else false
        }

        viewModel.actions.observe(viewLifecycleOwner) { actions ->
            when (actions) {
                is DrinksActions.DataLoaded -> {
                    hideLoader()
                    hideKeyboard()
                    adapter.updateData(actions.data)
                }
                DrinksActions.Error -> {
                    hideLoader()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.generic_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showLoader() {
        binding.swipeRefresh.isRefreshing = true
    }

    private fun hideLoader() {
        binding.swipeRefresh.isRefreshing = false
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}