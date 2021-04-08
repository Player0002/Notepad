package com.danny.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.danny.note.data.model.Note
import com.danny.note.databinding.FragmentEditBinding
import com.danny.note.presentation.adapter.FilterAdapter
import com.danny.note.presentation.viewModel.NoteViewModel


class EditFragment : Fragment() {

    private lateinit var binding : FragmentEditBinding
    private lateinit var tagAdapter : FilterAdapter
    private lateinit var viewModel : NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditBinding.bind(view)
        tagAdapter = (activity as MainActivity).editorAdapter
        viewModel = (activity as MainActivity).viewModel

        tagAdapter.setOnAddButtonPress {
            findNavController().navigate(R.id.action_editFragment_to_colorFragment, bundleOf("code" to "edit"))
        }
        tagAdapter.setOnTagPress {
            viewModel.removeEdit(it)
        }

        viewModel.selectedEdit.observe(viewLifecycleOwner) {
            tagAdapter.differ.submitList(it)
        }
        binding.save.setOnClickListener {
            with(binding) {
                viewModel.saveNote(Note(null, title.text.toString(), contents.text.toString(), tagAdapter.differ.currentList))
                findNavController().popBackStack()
            }
        }
        initRecyclerView();
    }

    private fun initRecyclerView() {
        binding.tags.apply {
            adapter = tagAdapter
            layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        }
    }
}