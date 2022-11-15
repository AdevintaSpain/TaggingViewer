package com.adevinta.android.taggingviewer.internal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.adevinta.android.taggingviewer.R
import com.adevinta.android.taggingviewer.databinding.TaggingViewItemBinding
import com.adevinta.android.taggingviewer.internal.TagEntry

internal class TagEntryItemViewHolder(binding: TaggingViewItemBinding) : ViewHolder(binding.root) {

  fun bind(infoEntry: TagEntry.ItemEntry) {
    val binding = TaggingViewItemBinding.bind(itemView)

    binding.trackingName.text = infoEntry.name
    binding.trackingIndicator.setImageResource(
      when (infoEntry) {
        is TagEntry.ItemEntry.Click -> R.drawable.tgv_marker_click
        is TagEntry.ItemEntry.Screen -> R.drawable.tgv_marker_screen
        is TagEntry.ItemEntry.Event -> R.drawable.tgv_marker_event
        is TagEntry.ItemEntry.UserAttribute -> R.drawable.tgv_marker_user_attribute
      }
    )
  }

  companion object {
    fun build(parent: ViewGroup): TagEntryItemViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      val binding = TaggingViewItemBinding.inflate(inflater)
      return TagEntryItemViewHolder(binding)
    }
  }
}
