package com.danny.note.presentation.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danny.note.R
import com.danny.note.data.model.Color
import com.danny.note.data.model.Note
import com.danny.note.databinding.PreviewLayoutBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class PreviewAdapter : RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    inner class PreviewViewHolder(private val binding: PreviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date =
                Instant.ofEpochMilli(note.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
            val list = listOf("월", "화", "수", "목", "금", "토", "일")
            binding.apply {
                title.text = note.title
                contents.text = note.contents
                dateTime.text = binding.root.context.getString(
                    R.string.date,
                    formatter.format(date),
                    list[date.dayOfWeek.value - 1]
                ) //"${formatter.format(date)} (${list[date.dayOfWeek.value - 1]})"
                tags.apply {
                    layoutManager = LinearLayoutManager(
                        binding.root.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter = PreviewChildAdapter().apply { differ.submitList(note.tags) }
                }
                root.setOnLongClickListener {
                    AlertDialog.Builder(root.context).setTitle("Delete")
                        .setMessage("${note.title} 노트를 지울까요?").setPositiveButton("Y") { _, _ ->
                            onConfirm?.invoke(note)
                        }.setNegativeButton("N") { _, _ ->

                        }.show()
                    return@setOnLongClickListener false
                }
                root.setOnClickListener {
                    onClick?.invoke(note)
                }
            }
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    var currentFilter = listOf<Color>()
    var databaseList = listOf<Note>()
    private var onConfirm: ((Note) -> Unit)? = null

    fun setOnConfirm(callback: (Note) -> Unit) {
        onConfirm = callback
    }

    private var onClick: ((Note) -> Unit)? = null

    fun setOnClick(callback: (Note) -> Unit) {
        onClick = callback
    }

    fun submitList(filter: List<Color> = currentFilter, list: List<Note> = databaseList) {
        currentFilter = filter
        databaseList = list
        if (filter.isEmpty()) differ.submitList(list)
        else {
            val newList = list.filter { cmp ->
                val filtered =
                    (cmp.tags + filter).groupBy { it }.filter { it.value.size > 1 }
                filtered.size == filter.size
            }
            differ.submitList(newList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val layout =
            PreviewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}
