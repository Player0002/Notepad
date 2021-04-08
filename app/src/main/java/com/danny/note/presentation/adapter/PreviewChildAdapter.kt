package com.danny.note.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.data.model.Color
import com.danny.note.databinding.PreviewItemBinding

class PreviewChildAdapter : RecyclerView.Adapter<PreviewChildAdapter.PreviewChildViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Color>() {
        override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class PreviewChildViewHolder(private val binding : PreviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(color : Color) {
            binding.icon.background.setTint(android.graphics.Color.argb(255, color.r, color.g, color.b))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewChildViewHolder {
        return PreviewChildViewHolder(PreviewItemBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: PreviewChildViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}