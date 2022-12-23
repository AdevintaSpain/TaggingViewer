package com.adevinta.android.taggingviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.adevinta.android.taggingviewer.internal.TrackingDispatcher

class DetailedTaggingComposeActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val eventsState by TrackingDispatcher.entriesData().observeAsState()
    }
  }
}
