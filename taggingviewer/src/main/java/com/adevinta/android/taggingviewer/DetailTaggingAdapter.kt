package com.adevinta.android.taggingviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.taggingviewer.databinding.TaggingDetailedItemBinding
import com.adevinta.android.taggingviewer.internal.TagEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class DetailTaggingAdapter : RecyclerView.Adapter<DetailTaggingViewHolder>() {
  var entries: List<TagEntry> = mutableListOf()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemCount() = entries.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTaggingViewHolder {
    val binding = TaggingDetailedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return DetailTaggingViewHolder(binding)
  }

  override fun onBindViewHolder(holder: DetailTaggingViewHolder, position: Int) {
    val listPosition = when (position) {
      0 -> ListPosition.FIRST
      itemCount - 1 -> ListPosition.LAST
      else -> ListPosition.MIDDLE
    }
    holder.bind(entries[position], listPosition)
  }
}

internal class DetailTaggingViewHolder(binding: TaggingDetailedItemBinding) : RecyclerView.ViewHolder(binding.root) {

  private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

  fun bind(tagEntry: TagEntry, listPosition: ListPosition) {
    val binding = TaggingDetailedItemBinding.bind(itemView)

    binding.detailItemTitle.text = tagEntry.name

    with(binding.detailItemExtra) {
      if (tagEntry is TagEntry.ItemEntry) {
        if (tagEntry.details.isEmpty()) {
          visibility = View.GONE
        } else {
          visibility = View.VISIBLE
          text = tagEntry.details
            .map { (key, value) -> "$key: $value" }
            .joinToString("\n")
        }
      }
    }

    binding.detailItemTime.text = timeFormat.format(Date(tagEntry.timestamp))
    val version = tagEntry.version?.toString() ?: "-"
    binding.detailItemVersion.text = binding.detailItemVersion.context.getString(
      R.string.tagging_viewer_item_version, version
    )

    binding.detailItemIcon.setImageResource(
      when (tagEntry) {
        is TagEntry.ItemEntry.Click -> R.drawable.tgv_marker_click
        is TagEntry.ItemEntry.Event -> R.drawable.tgv_marker_event
        is TagEntry.ItemEntry.Screen -> R.drawable.tgv_marker_screen
        is TagEntry.ItemEntry.UserAttribute -> R.drawable.tgv_marker_user_attribute
        else -> error("No icon assigned to $tagEntry")
      }
    )

    binding.detailItemLineBottom.visibility = if (listPosition == ListPosition.LAST) {
      View.INVISIBLE
    } else {
      View.VISIBLE
    }
  }
}
