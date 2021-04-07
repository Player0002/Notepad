package com.danny.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
            layoutManager = LinearLayoutManager(activity)
        }
    }
}