package com.adevinta.android.taggingviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.taggingviewer.databinding.TaggingActivityDetailedBinding
import com.adevinta.android.taggingviewer.filter.TaggingViewerFilterListBottomSheet
import com.adevinta.android.taggingviewer.internal.TagEntry
import com.adevinta.android.taggingviewer.internal.TrackingDispatcher
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailedTaggingActivity : AppCompatActivity() {

  private val adapter: DetailTaggingAdapter = DetailTaggingAdapter()

  private var itemTypes: MutableMap<String, Boolean> = mutableMapOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = TaggingActivityDetailedBinding.inflate(layoutInflater)
    setContentView(binding.root)
    title = "Tagging Viewer"

    binding.detailedTaggingListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.detailedTaggingListView.adapter = adapter

    TrackingDispatcher.entriesData().observe(
      this,
      Observer { entries ->
        val filteredEntries = entries
          .sortedByDescending { it.timestamp }
          .filterNot { it is TagEntry.SeparatorEntry }
          .filter { itemTypes[it.name] ?: true }

        adapter.entries = filteredEntries

        itemTypes = filteredEntries
          .map { it.name }
          .distinct()
          .associateWith { true }
          .toMutableMap()

        (this as MenuHost).invalidateMenu()
      }
    )


    addMenuProvider(object : MenuProvider {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_detailed_tagging, menu)
      }

      override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)

        menu.findItem(R.id.menu_filter).isVisible = itemTypes.isNotEmpty()
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
          R.id.menu_remove -> {
            TaggingViewer.clearAll()
            adapter.entries = listOf()
            true
          }
          R.id.menu_filter -> {
            showFilterList()
            true
          }
          else -> false
        }
      }
    }, this)
  }

  private fun showFilterList() {
    TaggingViewerFilterListBottomSheet.show(
      fm = supportFragmentManager,
      itemTypes = itemTypes,
      onTypeVisibilityChanged = { type, visible ->
        itemTypes[type] = visible
        adapter.filter = itemTypes
      },
    )
  }
}

internal class DetailTaggingAdapter : RecyclerView.Adapter<DetailTaggingViewHolder>() {
  var filter: MutableMap<String, Boolean> = mutableMapOf()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  var entries: List<TagEntry> = mutableListOf()
    get() = field.filter { filter[it.name] ?: true }
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemCount() = entries.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTaggingViewHolder {
    return DetailTaggingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tagging_detailed_item, parent, false))
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

internal class DetailTaggingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

  fun bind(tagEntry: TagEntry, listPosition: ListPosition) {
    itemView.findViewById<TextView>(R.id.detailItemTitle).text = tagEntry.name

    itemView.findViewById<TextView>(R.id.detailItemExtra).apply {
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

    itemView.findViewById<TextView>(R.id.detailItemTime).text = timeFormat.format(Date(tagEntry.timestamp))

    itemView.findViewById<ImageView>(R.id.detailItemIcon).setImageResource(
      when (tagEntry) {
        is TagEntry.ItemEntry.Click -> R.drawable.tgv_marker_click
        is TagEntry.ItemEntry.Event -> R.drawable.tgv_marker_event
        is TagEntry.ItemEntry.Screen -> R.drawable.tgv_marker_screen
        is TagEntry.ItemEntry.UserAttribute -> R.drawable.tgv_marker_user_attribute
        else -> error("No icon assigned to $tagEntry")
      }
    )

    itemView.findViewById<View>(R.id.detailItemLineBottom).visibility =
      if (listPosition == ListPosition.LAST) {
        View.INVISIBLE
      } else {
        View.VISIBLE
      }
  }
}

internal enum class ListPosition {
  FIRST, LAST, MIDDLE
}
