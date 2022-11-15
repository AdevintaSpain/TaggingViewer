package com.adevinta.android.taggingviewer.internal.view

import androidx.recyclerview.widget.DiffUtil
import com.adevinta.android.taggingviewer.internal.TagEntry

class TagEntryDiffUtilsCallback : DiffUtil.ItemCallback<TagEntry>() {

  override fun areItemsTheSame(oldItem: TagEntry, newItem: TagEntry): Boolean {
    return if (oldItem is TagEntry.SeparatorEntry && newItem is TagEntry.SeparatorEntry) {
      oldItem.name == newItem.name
    } else if (oldItem is TagEntry.ItemEntry && newItem is TagEntry.ItemEntry) {
      oldItem.name == newItem.name && oldItem.details == newItem.details
    } else {
      false
    }
  }

  override fun areContentsTheSame(oldItem: TagEntry, newItem: TagEntry): Boolean {
    return if (oldItem is TagEntry.SeparatorEntry && newItem is TagEntry.SeparatorEntry) {
      oldItem.name == newItem.name
    } else if (oldItem is TagEntry.ItemEntry && newItem is TagEntry.ItemEntry) {
      oldItem.name == newItem.name && oldItem.details == newItem.details
    } else {
      oldItem == newItem
    }
  }
}
