package com.adevinta.android.taggingviewer.internal.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

internal class IgnoreTouchLayout : FrameLayout {
  constructor(context: Context) : super(context) {}

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

  override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
    return true
  }
}
