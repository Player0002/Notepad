package com.danny.note.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.data.model.Color
import com.danny.note.data.model.Note
import com.danny.note.databinding.PreviewLayoutBinding

class PreviewAdapter : RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
    inner class PreviewViewHolder(private val binding : PreviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note : Note) {
            binding.apply {
                title.text = note.title
                contents.text = note.contents
                tags.apply {
                    layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = PreviewChildAdapter().apply { differ.submitList(note.tags) }
                }
            }
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val layout = PreviewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}
