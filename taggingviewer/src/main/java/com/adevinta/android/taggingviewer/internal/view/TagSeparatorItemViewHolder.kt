package com.adevinta.android.taggingviewer.internal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.taggingviewer.databinding.TaggingViewSeparatorItemBinding
import com.adevinta.android.taggingviewer.internal.TagEntry

internal class TagSeparatorItemViewHolder(binding: TaggingViewSeparatorItemBinding) : RecyclerView.ViewHolder(binding.root) {

  fun bind(separatorEntry: TagEntry.SeparatorEntry) {
    val binding = TaggingViewSeparatorItemBinding.bind(itemView)
    if (separatorEntry is TagEntry.SeparatorEntry.Named) {
      binding.trackingActivityName.text = separatorEntry.name
      binding.trackingActivitySeparator.visibility = View.VISIBLE
    } else {
      binding.trackingActivityName.text = ""
      binding.trackingActivitySeparator.visibility = View.INVISIBLE
    }
  }

  companion object {
    fun build(parent: ViewGroup): TagSeparatorItemViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      val view = TaggingViewSeparatorItemBinding.inflate(inflater)
      return TagSeparatorItemViewHolder(view)
    }
  }
}
