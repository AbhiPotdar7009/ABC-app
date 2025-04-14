package com.example.abc_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abc_app.databinding.ItemListBinding
import com.example.abc_app.domain.model.ListItem


class LabelAdapter(var labels: List<ListItem>) :
    RecyclerView.Adapter<LabelAdapter.LabelViewHolder>() {

    inner class LabelViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val subtitle = binding.subtitle
        val image = binding.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LabelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        holder.title.text = labels[position].title
        holder.subtitle.text = labels[position].subtitle
        holder.image.setImageResource(labels[position].imageRes)
    }

    override fun getItemCount(): Int = labels.size

    fun update(newList: List<ListItem>) {
        labels = newList
        notifyDataSetChanged()
    }
}