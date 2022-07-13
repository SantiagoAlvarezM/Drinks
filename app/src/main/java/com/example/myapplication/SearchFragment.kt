package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.model.DrinksActions

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DrinkAdapter
    private val viewModel: DrinkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DrinkAdapter { drink ->
            findNavController().navigate(R.id.action_SearchFragment_to_SecondFragment)
        }
        binding.recyclerSearch.adapter = adapter
        binding.edittextFirst.doAfterTextChanged {
            viewModel.searchDrinks(it.toString())
        }
        viewModel.actions.observe(viewLifecycleOwner) { actions ->
            when (actions) {
                is DrinksActions.DataLoaded -> {
                    adapter.updateData(actions.data)
                }
                DrinksActions.Error -> Toast.makeText(
                    requireContext(),
                    getString(R.string.generic_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}