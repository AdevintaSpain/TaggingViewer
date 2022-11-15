package com.adevinta.android.taggingviewer.internal

import android.app.Activity
import android.view.ViewGroup
import androidx.core.view.iterator
import androidx.lifecycle.LifecycleOwner
import com.adevinta.android.taggingviewer.R
import com.adevinta.android.taggingviewer.internal.util.observe
import com.adevinta.android.taggingviewer.internal.view.TaggingOverlayListView

/**
 * Here be dragons.
 * This utility component lets you render the taggins on screen, so the BA engineer can see what's being tracked without running Charles
 * Proxy and searching requests.
 * It's not a production feature. It's usually disabled. It's not clean. It's not readable. It's not efficient. It's just helpful in a few
 * cases.
 */
internal class TaggingViewerUiComponent(private val taggingOverlayListView: TaggingOverlayListView, lifecycleOwner: LifecycleOwner) {

  init {
    TrackingDispatcher.entriesData().observe(lifecycleOwner) { entries ->
      taggingOverlayListView.update(entries)
    }

    TaggingConfig.alphaLiveData().observe(lifecycleOwner) { alpha ->
      taggingOverlayListView.alpha = alpha
    }
  }

  companion object {
    fun createAndInject(activity: Activity, lifecycle: LifecycleOwner): TaggingViewerUiComponent {
      val rootView = activity.findViewById<ViewGroup>(android.R.id.content)

      // create the wrapper and tagging list view
      val taggingViewWrapper = activity.layoutInflater.inflate(R.layout.tagging_view_wrapper, rootView, false)
      val activityContainer = taggingViewWrapper.findViewById<ViewGroup>(R.id.tagging_viewer_activity_container)
      val taggingListView = taggingViewWrapper.findViewById<TaggingOverlayListView>(R.id.tagging_viewer_tags_list)

      rootView
        .iterator()
        .asSequence()
        .filterNotNull()
        .forEach {
          // remove the content view from the root
          rootView.removeView(it)
          // add the activityContent to the wrapper activity container
          activityContainer.addView(it, it.layoutParams)
        }

      // add the wrapper to the root
      rootView.addView(
        taggingViewWrapper,
        ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
      )

      return TaggingViewerUiComponent(taggingListView, lifecycle)
    }
  }
}
