package com.adevinta.android.taggingviewer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adevinta.android.taggingviewer.internal.TagEntry
import com.adevinta.android.taggingviewer.internal.TrackingDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailTaggingViewModel : ViewModel() {

  private var currentEntries: List<TagEntry> = emptyList()

  private var itemTypes: MutableMap<String, Boolean> = mutableMapOf()

  private val _flow: MutableStateFlow<List<TagEntry>> = MutableStateFlow(currentEntries)
  val flow: StateFlow<List<TagEntry>> = _flow.asStateFlow()

  private val _filtersShown: MutableStateFlow<Map<String, Boolean>?> = MutableStateFlow(null)
  val filterShown: StateFlow<Map<String, Boolean>?> = _filtersShown.asStateFlow()

  fun onInit(lifecycleOwner: LifecycleOwner) = viewModelScope.launch {
    TrackingDispatcher.entriesData().observe(lifecycleOwner) { entries ->
      currentEntries = entries

      itemTypes = currentEntries
        .map { it.name }
        .filterNot { it.isEmpty() }
        .distinct()
        .associateWith { key -> itemTypes[key] ?: true }
        .toMutableMap()

      sendEntries()
    }
  }

  private fun sendEntries() {
    _flow.value = currentEntries
      .sortedByDescending { it.timestamp }
      .filterNot { it is TagEntry.SeparatorEntry }
      .filter { itemTypes[it.name] ?: true }
  }

  fun clearAll() = viewModelScope.launch {
    TaggingViewer.clearAll()
  }

  fun onFilterUpdated(type: String, visible: Boolean) {
    itemTypes[type] = visible
    sendEntries()
  }

  fun onShowFilters() = viewModelScope.launch {
    _filtersShown.value = itemTypes
  }

  fun onFiltersShown() = viewModelScope.launch {
    _filtersShown.value = null
  }
}
