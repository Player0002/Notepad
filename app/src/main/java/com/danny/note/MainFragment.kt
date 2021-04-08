package com.danny.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.asFlow
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.databinding.FragmentMainBinding
import com.danny.note.presentation.adapter.FilterAdapter
import com.danny.note.presentation.adapter.PreviewAdapter
import com.danny.note.presentation.viewModel.NoteViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.flow.first

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: NoteViewModel

    private lateinit var filterAdapter: FilterAdapter
    private lateinit var previewAdapter: PreviewAdapter
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
        previewAdapter = (activity as MainActivity).previewAdapter

        filterAdapter.setOnAddButtonPress {
            findNavController().navigate(
                R.id.action_mainFragment_to_colorFragment,
                bundleOf("code" to "main")
            )
        }
        filterAdapter.setOnTagPress {
            viewModel.removeFilter(it)
        }

        previewAdapter.setOnConfirm {
            viewModel.deleteNote(it)
        }
        previewAdapter.setOnClick {
            findNavController().navigate(R.id.action_mainFragment_to_editFragment, bundleOf("note" to it))
        }

        viewModel.filteredEdit().observe(viewLifecycleOwner) {
            previewAdapter.differ.submitList(it)
            binding.previewItems.smoothScrollToPosition(0)
        }

        viewModel.selectedFilter.observe(viewLifecycleOwner) {
            filterAdapter.differ.submitList(it)
        }

        //for hide recyclerview
        binding.appbarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                binding.constraint.alpha =
                    (appBarLayout.totalScrollRange + verticalOffset) / appBarLayout.totalScrollRange.toFloat()
            }
        )

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_editFragment)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.filter.apply {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.previewItems.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = previewAdapter
        }
    }
}