package com.adevinta.android.taggingviewer.internal.view

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.adevinta.android.taggingviewer.internal.TagEntry

class TaggingAdapter : ListAdapter<TagEntry, ViewHolder>(TagEntryDiffUtilsCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (ItemViewType.ofId(viewType)) {
      ItemViewType.SEPARATOR -> TagSeparatorItemViewHolder.build(parent)
      ItemViewType.ITEM -> TagEntryItemViewHolder.build(parent)
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when (holder) {
      is TagSeparatorItemViewHolder -> holder.bind(getItem(position) as TagEntry.SeparatorEntry)
      is TagEntryItemViewHolder -> holder.bind(getItem(position) as TagEntry.ItemEntry)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is TagEntry.SeparatorEntry -> ItemViewType.SEPARATOR
      is TagEntry.ItemEntry -> ItemViewType.ITEM
    }.id
  }

  enum class ItemViewType(val id: Int) {
    SEPARATOR(1),
    ITEM(2);

    companion object {
      fun ofId(id: Int) = values().first { it.id == id }
    }
  }
}
