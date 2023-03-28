package com.adevinta.android.taggingviewer.internal.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

internal fun <T> LiveData<T>.observe(owner: LifecycleOwner, f: (T) -> Unit) {
  this.observe(
    owner,
    Observer { t -> t?.let(f) }
  )
}
