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

  private val _flow: MutableStateFlow<List<TagEntry>> = MutableStateFlow(emptyList())
  val flow: StateFlow<List<TagEntry>> = _flow.asStateFlow()

  fun onInit(lifecycleOwner: LifecycleOwner) = viewModelScope.launch {
    TrackingDispatcher.entriesData().observe(lifecycleOwner) { entries ->
      _flow.value = entries
    }
  }

  fun clearAll() = viewModelScope.launch {
    TaggingViewer.clearAll()
  }
}
