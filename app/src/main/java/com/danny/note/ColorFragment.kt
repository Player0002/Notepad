package com.danny.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.databinding.FragmentColorBinding
import com.danny.note.presentation.adapter.SearchAdapter
import com.danny.note.presentation.viewModel.NoteViewModel

class ColorFragment : Fragment() {

    lateinit var binding : FragmentColorBinding
    lateinit var viewModel : NoteViewModel
    lateinit var searchAdapter : SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_color, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentColorBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        searchAdapter = (activity as MainActivity).searchAdapter

        searchAdapter.setOnClickListener {
            viewModel.addFilter(it)
        }

        viewModel.savedColor().observe(viewLifecycleOwner) {
            searchAdapter.differ.submitList(it.filter { color ->
                color.name.contains(binding.searchText.text.toString())
            })
        }

        binding.searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.savedColor().observe(viewLifecycleOwner) {
                    searchAdapter.differ.submitList(it.filter { color -> color.name.contains(text.toString()) })
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_colorFragment_to_addFragment)
        }
        viewModel.transitionRequest.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_colorFragment_to_mainFragment)
            }
        }

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.removeColor(searchAdapter.differ.currentList[viewHolder.adapterPosition])
            }
        }

        viewModel.toastRequest.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let{ str -> Toast.makeText(context, str, Toast.LENGTH_SHORT).show() }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.searchRecycler)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.searchRecycler.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}