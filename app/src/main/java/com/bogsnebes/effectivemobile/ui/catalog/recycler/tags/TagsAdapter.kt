package com.bogsnebes.effectivemobile.ui.catalog.recycler.tags

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.effectivemobile.R

class TagsAdapter(
    private val tags: List<Tag>,
    private val onTagSelected: (String) -> Unit,
    private val onTagDeselected: () -> Unit
) :
    RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = if (viewType == VIEW_TYPE_SELECTED) {
            layoutInflater.inflate(R.layout.recycler_item_catalog_category_selected, parent, false)
        } else {
            layoutInflater.inflate(R.layout.recycler_item_catalog_category, parent, false)
        }
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tags[position]
        holder.bind(tag, position == selectedPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == selectedPosition) VIEW_TYPE_SELECTED else VIEW_TYPE_UNSELECTED
    }

    override fun getItemCount(): Int = tags.size

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tag: Tag, isSelected: Boolean) {
            itemView.findViewById<TextView>(R.id.textView6).text = tag.name
            itemView.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                if (isSelected) {
                    onTagDeselected()
                    selectedPosition = RecyclerView.NO_POSITION
                } else {
                    if (selectedPosition != RecyclerView.NO_POSITION) {
                        notifyItemChanged(selectedPosition)
                    }
                    selectedPosition = adapterPosition
                    onTagSelected(tag.filter)
                }
                notifyItemChanged(adapterPosition)
            }
            if (isSelected) {
                itemView.findViewById<ImageView>(R.id.imageView3)?.setOnClickListener {
                    onTagDeselected()
                    selectedPosition = RecyclerView.NO_POSITION
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_SELECTED = 1
        private const val VIEW_TYPE_UNSELECTED = 0
    }
}

