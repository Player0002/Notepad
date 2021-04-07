package com.danny.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.databinding.FragmentMainBinding
import com.danny.note.presentation.adapter.FilterAdapter
import com.danny.note.presentation.viewModel.NoteViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var filterAdapter: FilterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        filterAdapter = (activity as MainActivity).filterAdapter

        filterAdapter.setOnAddButtonPress {
            findNavController().navigate(R.id.action_mainFragment_to_colorFragment)
        }

        filterAdapter.setOnTagPress { viewModel.removeFilter(it) }

        viewModel.selectedFilter.observe(viewLifecycleOwner) {
            filterAdapter.differ.submitList(it)
        }

        viewModel.toastRequest.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let{ str -> Toast.makeText(context, str, Toast.LENGTH_SHORT).show() }
        }
        //for hide recyclerview
        binding.appbarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                binding.constraint.alpha =
                     (appBarLayout.totalScrollRange+ verticalOffset) / appBarLayout.totalScrollRange.toFloat()
            }
        )

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.filter.apply {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}