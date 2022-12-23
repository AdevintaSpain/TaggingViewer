package com.adevinta.android.taggingviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.adevinta.android.taggingviewer.internal.TagEntry
import com.adevinta.android.taggingviewer.internal.TrackingDispatcher

class DetailedTaggingComposeActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val eventsState: List<TagEntry>? by TrackingDispatcher.entriesData().observeAsState()

      eventsState?.let { items ->
        LazyColumn {
          items(items) { item ->
            Text(text = item.name)
          }
        }
      }
    }
  }
}
