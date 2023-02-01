package com.adevinta.android.taggingviewer.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.taggingviewer.databinding.TaggingViewFilterRowBinding

internal class TaggingViewFilterAdapter(
  private val onTypeVisibilityChanged: (String, Boolean) -> Unit,
) : RecyclerView.Adapter<TaggingViewFilterAdapter.ViewHolder>() {

  var items: Map<String, Boolean> = emptyMap()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = TaggingViewFilterRowBinding.inflate(layoutInflater, parent, false)
    return ViewHolder(binding, onTypeVisibilityChanged)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val key = items.keys.toList()[position]
    holder.bind(key, items[key] ?: true)
  }

  override fun getItemCount(): Int = items.size

  class ViewHolder(
    binding: TaggingViewFilterRowBinding,
    private val onTypeVisibilityChanged: (String, Boolean) -> Unit,
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String, checked: Boolean) {
      TaggingViewFilterRowBinding.bind(itemView).apply {
        text.isChecked = checked
        text.text = item
        text.setOnCheckedChangeListener { _, isChecked ->
          onTypeVisibilityChanged(item, isChecked)
        }
      }
    }
  }
}
