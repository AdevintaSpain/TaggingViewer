package com.adevinta.android.taggingviewer.internal.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.taggingviewer.internal.TagEntry

internal class TaggingOverlayListView : RecyclerView {

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init()
  }

  private fun init() {
    layoutManager = LinearLayoutManager(context)
    adapter = TaggingAdapter()
  }

  fun update(entries: List<TagEntry>) {
    val limitedEntries = entries.takeLast(MAX_NUMBER_OF_ENTRIES)

    (adapter as TaggingAdapter).submitList(limitedEntries)
  }

  companion object {
    private const val MAX_NUMBER_OF_ENTRIES = 10
  }
}
