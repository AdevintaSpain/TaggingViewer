package com.adevinta.android.taggingviewer.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.adevinta.android.taggingviewer.TaggingViewer.tagScreen

class DetailActivity : AppCompatActivity() {

  private lateinit var extraText: String
  private lateinit var extraItemName: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    extraText = intent.getStringExtra(EXTRA_TEXT)!!
    extraItemName = intent.getStringExtra(EXTRA_ITEM_NAME)!!

    findViewById<TextView>(R.id.label).text = extraText
  }

  override fun onStart() {
    super.onStart()
    tagScreen(
      name = "Detail screen",
      details = mapOf("itemName" to extraItemName, "text" to extraText),
      version = 2,
    )
  }

  companion object {
    const val EXTRA_TEXT = "EXTRA_TEXT"
    const val EXTRA_ITEM_NAME = "EXTRA_ITEM_NAME"
  }
}
