package com.danny.note.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.data.model.Color
import com.danny.note.databinding.TagLayoutBinding

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    inner class FilterViewHolder(private val binding: TagLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Color) {
            binding.apply {
                colorName.text = color.name
                this.color.background.setTint(
                    android.graphics.Color.argb(
                        255,
                        color.r,
                        color.g,
                        color.b
                    )
                )
                root.setOnClickListener {
                    onTagPress?.invoke(color)
                }
            }
        }
        fun bindButton() {
            binding.apply { 
                colorName.text = "색 추가"
                color.background.setTint(android.graphics.Color.LTGRAY)
                root.setOnClickListener {
                    onAddButtonPress?.invoke()
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Color>() {
        override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = TagLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }


    private var onAddButtonPress :(() -> Unit)? = null

    fun setOnAddButtonPress(listener : () -> Unit) {
        onAddButtonPress = listener
    }

    private var onTagPress :((Color) -> Unit)? = null

    fun setOnTagPress(listener : (Color) -> Unit) {
        onTagPress = listener
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        if(differ.currentList.size == position) {
            holder.bindButton()
        } else {
            holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size + 1
}