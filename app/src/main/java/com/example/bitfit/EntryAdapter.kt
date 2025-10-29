package com.example.bitfit.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.data.Entry
import com.example.bitfit.databinding.ItemEntryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EntryAdapter : RecyclerView.Adapter<EntryAdapter.VH>() {
    private val items = mutableListOf<Entry>()

    fun submit(list: List<Entry>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class VH(val binding: ItemEntryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvValue.text = item.value.toString()
        holder.binding.tvNotes.text = item.notes.orEmpty()
        val df = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
        holder.binding.tvDate.text = df.format(Date(item.timestamp))
    }

    override fun getItemCount(): Int = items.size
}
