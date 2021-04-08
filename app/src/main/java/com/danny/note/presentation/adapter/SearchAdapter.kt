package com.danny.note.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.data.model.Color
import com.danny.note.databinding.SearchItemLayoutBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Color>() {
        override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class SearchViewHolder(private val binding: SearchItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Color) {
            binding.apply {
                println(color.name)
                colorName.text = color.name
                colorView.background.setTint(
                    android.graphics.Color.argb(
                        255,
                        color.r,
                        color.g,
                        color.b
                    )
                )
                root.setOnClickListener {
                    onClickListener?.let {
                        it(color)
                    }
                }
            }
        }
    }

    private var onClickListener : ((Color) -> Unit)? = null;

    fun setOnClickListener(callback : (Color) -> Unit) {
        onClickListener = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}