package com.danny.note

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.danny.note.databinding.ActivityMainBinding
import com.danny.note.presentation.adapter.FilterAdapter
import com.danny.note.presentation.adapter.PreviewAdapter
import com.danny.note.presentation.adapter.SearchAdapter
import com.danny.note.presentation.viewModel.NoteViewModel
import com.danny.note.presentation.viewModel.NoteViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: NoteViewModelFactory

    @Inject
    lateinit var filterAdapter: FilterAdapter

    @Inject
    lateinit var editorAdapter: FilterAdapter

    @Inject
    lateinit var previewAdapter: PreviewAdapter

    @Inject
    lateinit var searchAdapter: SearchAdapter

    val viewModel: NoteViewModel by viewModels { factory }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.toastRequest.observe(this) {
            it.getContentIfNotHandled()
                ?.let { str -> Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show() }
        }
    }
}