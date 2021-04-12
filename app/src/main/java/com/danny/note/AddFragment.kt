package com.danny.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danny.note.databinding.FragmentAddBinding
import com.danny.note.presentation.viewModel.NoteViewModel
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorListener


class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        binding.colorPicker.setColorListener(
            object : ColorListener {
                override fun onColorSelected(color: Int, fromUser: Boolean) {
                    val envelope = ColorEnvelope(color)
                    binding.colorStr.text = getString(
                        R.string.color_code,
                        envelope.hexCode.substring(1)
                    ) //"#${envelope.hexCode.substring(1)}"
                    viewModel.getColor(envelope)
                }
            }
        )
        binding.submit.setOnClickListener {
            viewModel.addColor(binding.inputName.text.toString())
        }

        viewModel.selectedColor.observe(viewLifecycleOwner) {
            binding.inputName.setText(it.name)
        }

        viewModel.transitionRequest.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
            }
        }

    }
}