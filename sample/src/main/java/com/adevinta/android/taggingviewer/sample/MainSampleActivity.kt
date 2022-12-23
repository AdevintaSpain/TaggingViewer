package com.adevinta.android.taggingviewer.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.adevinta.android.taggingviewer.TaggingViewer

class MainSampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById<SwitchCompat>(R.id.overlayEnabledSwitch).setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        TaggingViewer.enableOverlay()
      } else {
        TaggingViewer.disableOverlay()
      }
    }
    findViewById<SwitchCompat>(R.id.namedSeparatorEnabledSwitch).setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        TaggingViewer.enableNamedSeparator()
      } else {
        TaggingViewer.disableNamedSeparator()
      }
    }
    findViewById<View>(R.id.clearAllButton).setOnClickListener {
      TaggingViewer.clearAll()
    }

    findViewById<SeekBar>(R.id.opacitySeekBar).setOnSeekBarChangeListener(
      object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
          if (fromUser) {
            val alpha = progress.toFloat() / seekBar.max.toFloat()
            TaggingViewer.setAlpha(alpha)
          }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
      }
    )

    findViewById<View>(R.id.show_the_merge_button).setOnClickListener {
      TaggingViewer.tagClick("Open merge button")
      startActivity(Intent(this, MergeActivity::class.java))
    }
    findViewById<View>(R.id.show_the_list_button).setOnClickListener {
      TaggingViewer.tagClick("Open list button")
      startActivity(Intent(this, ListActivity::class.java))
    }

    findViewById<View>(R.id.addRandomTagsButton).setOnClickListener {
      addRandomEvents()
    }
    findViewById<View>(R.id.addScreenButton).setOnClickListener {
      TaggingViewer.tagScreen("Some screen", mapOf("property name" to "property value"))
    }
    findViewById<View>(R.id.addClickButton).setOnClickListener {
      TaggingViewer.tagClick("Some Click", mapOf("property name" to "property value"))
    }
    findViewById<View>(R.id.addEventButton).setOnClickListener {
      TaggingViewer.tagEvent("Some Event", mapOf("property name" to "property value"))
    }
    findViewById<View>(R.id.addIdentifyButton).setOnClickListener {
      TaggingViewer.tagUserAttribute("Some Identify", mapOf("property name" to "property value"))
    }

    findViewById<View>(R.id.show_detailed_view).setOnClickListener {
      TaggingViewer.tagClick("Detailed Activity Clicked")
      TaggingViewer.showDetailedActivity(this)
    }
  }

  override fun onStart() {
    super.onStart()
    TaggingViewer.tagScreen("Home screen")
    findViewById<SwitchCompat>(R.id.overlayEnabledSwitch).isChecked = TaggingViewer.isOverlayEnabled()
  }

  private fun addRandomEvents() {
    TaggingViewer.tagUserAttribute("identify", details = mapOf("name" to "Rodrigo", "email" to "rodri@vaticancity.it"))
    TaggingViewer.tagClick("Something clicked")
    TaggingViewer.tagClick(LIPSUM)
    TaggingViewer.tagEvent("This happened", details = mapOf("key" to "value", "another_key" to "more value"))
    TaggingViewer.tagScreen("Screen viewed")
    TaggingViewer.tagClick("Another click")
    TaggingViewer.tagEvent(
      "One more event",
      details = mapOf("more_key" to "just a value", "keys_everywhere" to "value values values", "guess_what" to "value!")
    )
    TaggingViewer.tagClick("Something clicked")
    TaggingViewer.tagClick("Another click")
    TaggingViewer.tagEvent("This happened")
    TaggingViewer.tagEvent("One more event")
  }

  companion object {
    private val LIPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum mollis iaculis nunc, eget consequat ipsum " +
      "vestibulum quis. Vestibulum congue metus id diam ultrices, efficitur dapibus ipsum consequat. Cras a massa magna. Pellentesque ac " +
      "lectus vel turpis finibus blandit vitae ac risus. Nunc ut risus lacinia, malesuada neque non, ullamcorper libero. Integer dictum " +
      "mi et interdum fringilla. Mauris vel quam dapibus, dapibus odio eget, vehicula mi. Sed malesuada, nisi et facilisis molestie, " +
      "lacus augue ornare tellus, eu vehicula ligula sapien ac libero. Proin sem nulla, pellentesque non justo sed, finibus dapibus elit." +
      " Fusce nunc sapien, pulvinar quis quam at, tempor pulvinar risus. Phasellus sit amet tempus purus. Sed eros ligula, pretium nec " +
      "orci eu, cursus lacinia sem. Ut varius, nibh vitae convallis viverra, lacus lectus imperdiet ligula, at ullamcorper lectus " +
      "nisl nec ero"
  }
}
