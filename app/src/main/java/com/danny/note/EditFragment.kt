package com.danny.note

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.danny.note.data.model.Note
import com.danny.note.databinding.FragmentEditBinding
import com.danny.note.presentation.adapter.FilterAdapter
import com.danny.note.presentation.viewModel.NoteViewModel
import java.sql.Date
import java.time.DateTimeException
import java.util.*


class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var tagAdapter: FilterAdapter
    private lateinit var viewModel: NoteViewModel
    private lateinit var inputMethodManager : InputMethodManager

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.get("note")?.let { note = it as Note }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditBinding.bind(view)
        tagAdapter = (activity as MainActivity).editorAdapter
        viewModel = (activity as MainActivity).viewModel
        inputMethodManager = (activity as MainActivity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        tagAdapter.setOnAddButtonPress {
            findNavController().navigate(
                R.id.action_editFragment_to_colorFragment,
                bundleOf("code" to "edit")
            )
        }
        tagAdapter.setOnTagPress {
            viewModel.removeEdit(it)
        }

        viewModel.selectedEdit.observe(viewLifecycleOwner) {
            tagAdapter.differ.submitList(it)
        }
        binding.save.setOnClickListener {
            with(binding) {
                viewModel.saveNote(
                    Note(
                        note?.id,
                        title.text.toString(),
                        contents.text.toString(),
                        tagAdapter.differ.currentList,
                        Calendar.getInstance().timeInMillis
                    )
                )

            }
        }
        viewModel.editTransitionRequest.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                findNavController().popBackStack()
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            findNavController().popBackStack()
        }

        note?.let { note ->
            with(binding) {
                if (title.text.isEmpty())
                    title.setText(note.title)
                if (contents.text.isEmpty())
                    contents.setText(note.contents)
                if (tagAdapter.differ.currentList.isEmpty())
                    viewModel.setEdit(note.tags)
            }
        }
        initRecyclerView();
    }

    override fun onDestroy() {
        tagAdapter.differ.submitList(listOf())
        viewModel.clearEdit();
        super.onDestroy()
    }

    private fun initRecyclerView() {
        binding.tags.apply {
            adapter = tagAdapter
            layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        }
    }
}