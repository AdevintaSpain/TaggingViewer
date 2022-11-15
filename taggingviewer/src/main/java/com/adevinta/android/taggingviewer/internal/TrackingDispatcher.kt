package com.adevinta.android.taggingviewer.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

internal object TrackingDispatcher {

  private val entriesLiveData = MutableLiveData<List<TagEntry>>()
  private val entries = mutableListOf<TagEntry>()

  fun entriesData(): LiveData<List<TagEntry>> = entriesLiveData

  fun addEntry(entry: TagEntry) {
    entries += entry
  }

  fun refreshScreen() {
    entriesLiveData.postValue(entries)
  }

  fun clearScreen() {
    entriesLiveData.postValue(emptyList())
  }

  fun clearAll() {
    entries.clear()
    entriesLiveData.postValue(emptyList())
  }
}
