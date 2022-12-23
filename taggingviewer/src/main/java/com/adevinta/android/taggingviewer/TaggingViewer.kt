package com.adevinta.android.taggingviewer

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.lifecycle.LifecycleOwner
import com.adevinta.android.taggingviewer.internal.TagEntry
import com.adevinta.android.taggingviewer.internal.TaggingConfig
import com.adevinta.android.taggingviewer.internal.TaggingViewerUiComponent
import com.adevinta.android.taggingviewer.internal.TrackingDispatcher
import com.adevinta.android.taggingviewer.internal.util.ActivityLifecycleCallbacksAdapter
import java.lang.ref.WeakReference

object TaggingViewer {

  internal var isOverlayEnabled: Boolean = false
  internal var isActivitySeparatorEnabled: Boolean = true
  internal var isActivityNameSeparatorEnabled: Boolean = false
  internal var currentActivity: WeakReference<Activity>? = null

  private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacksAdapter() {
    override fun onActivityStarted(activity: Activity) {
      currentActivity = WeakReference(activity)
      if (isOverlayEnabled) {
        injectTaggingIntoActivity(activity)
      }
    }

    override fun onActivityResumed(activity: Activity) {
      if (isActivitySeparatorEnabled) {
        val entry = if (isActivityNameSeparatorEnabled) {
          TagEntry.SeparatorEntry.Named(activity.javaClass.simpleName ?: "")
        } else {
          TagEntry.SeparatorEntry.Space
        }
        TrackingDispatcher.addEntry(entry)
        if (isOverlayEnabled || isDetailedActivity(activity)) {
          TrackingDispatcher.refreshScreen()
        }
      }
    }
  }

  private fun injectTaggingIntoActivity(activity: Activity?) {
    if (activity is LifecycleOwner &&
      !isAlreadyInjected(activity) &&
      !isDetailedActivity(activity)
    ) {
      TaggingViewerUiComponent.createAndInject(activity, activity)
    }
  }

  private fun isDetailedActivity(activity: Activity): Boolean {
    return activity is DetailedTaggingActivity
  }

  internal fun init(context: Context) {
    context.application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
  }

  @JvmStatic
  @Deprecated(
    "The library is now enabled by default. Use [enableOverlay] to show the overlay events list.",
    ReplaceWith("enableOverlay(context)")
  )
  fun enable(context: Context) {
    enableOverlay()
  }

  @JvmStatic
  @Deprecated("The library can't be disabled now. Use [disableOverlay] to hide the overlay events list.", ReplaceWith("disableOverlay()"))
  fun disable(context: Context) {
    disableOverlay()
  }

  @JvmStatic
  fun isOverlayEnabled(): Boolean = isOverlayEnabled

  @JvmStatic
  fun enableOverlay() {
    isOverlayEnabled = true
    currentActivity?.get()?.let {
      injectTaggingIntoActivity(it)
    }
    TrackingDispatcher.refreshScreen()
  }

  @JvmStatic
  fun disableOverlay() {
    isOverlayEnabled = false
    TrackingDispatcher.clearScreen()
  }

  @JvmStatic
  fun enableNamedSeparator() {
    isActivityNameSeparatorEnabled = true
    TrackingDispatcher.refreshScreen()
  }

  @JvmStatic
  fun disableNamedSeparator() {
    isActivityNameSeparatorEnabled = false
    TrackingDispatcher.refreshScreen()
  }

  @JvmStatic
  fun setAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
    TaggingConfig.setAlpha(alpha)
  }

  @JvmStatic
  fun clearAll() {
    TrackingDispatcher.clearAll()
  }

  @JvmStatic
  @JvmOverloads
  fun tagClick(name: String, details: Map<String, String> = emptyMap()) {
    TrackingDispatcher.addEntry(TagEntry.ItemEntry.Click(name, details = details))
    if (isOverlayEnabled) {
      TrackingDispatcher.refreshScreen()
    }
  }

  @JvmStatic
  @JvmOverloads
  fun tagScreen(name: String, details: Map<String, String> = emptyMap()) {
    TrackingDispatcher.addEntry(TagEntry.ItemEntry.Screen(name, details = details))
    if (isOverlayEnabled) {
      TrackingDispatcher.refreshScreen()
    }
  }

  @JvmStatic
  @JvmOverloads
  fun tagEvent(name: String, details: Map<String, String> = emptyMap()) {
    TrackingDispatcher.addEntry(TagEntry.ItemEntry.Event(name, details = details))
    if (isOverlayEnabled) {
      TrackingDispatcher.refreshScreen()
    }
  }

  @JvmStatic
  @JvmOverloads
  fun tagUserAttribute(name: String, details: Map<String, String> = emptyMap()) {
    TrackingDispatcher.addEntry(TagEntry.ItemEntry.UserAttribute(name, details = details))
    if (isOverlayEnabled) {
      TrackingDispatcher.refreshScreen()
    }
  }

  @JvmStatic
  fun showDetailedActivity(context: Context) {
    context.startActivity(Intent(context, DetailedTaggingActivity::class.java))
  }

  private fun isAlreadyInjected(activity: Activity): Boolean {
    val rootView = activity.findViewById(android.R.id.content) as ViewGroup
    return rootView.findViewById<View>(R.id.tagging_viewer_wrapper) != null
  }

  private val Context.application
    get() = this.applicationContext as Application
}
