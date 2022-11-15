package com.adevinta.android.taggingviewer

import android.content.Context
import androidx.annotation.FloatRange

object TaggingViewer {

  @JvmStatic
  fun isOverlayEnabled(): Boolean = false

  @JvmStatic
  fun enableOverlay(context: Context) {
  }

  @JvmStatic
  fun disableOverlay() {
  }

  @JvmStatic
  fun enableNamedSeparator() {
  }

  @JvmStatic
  fun disableNamedSeparator() {
  }

  @JvmStatic
  fun setAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
  }

  @JvmStatic
  fun clearAll() {
  }

  @JvmStatic
  @JvmOverloads
  fun tagClick(name: String, details: Map<String, String> = emptyMap()) {
  }

  @JvmStatic
  @JvmOverloads
  fun tagScreen(name: String, details: Map<String, String> = emptyMap()) {
  }

  @JvmStatic
  @JvmOverloads
  fun tagEvent(name: String, details: Map<String, String> = emptyMap()) {
  }

  @JvmStatic
  @JvmOverloads
  fun tagUserAttribute(name: String, details: Map<String, String> = emptyMap()) {
  }

  @JvmStatic
  fun showDetailedActivity(context: Context) {
  }
}
