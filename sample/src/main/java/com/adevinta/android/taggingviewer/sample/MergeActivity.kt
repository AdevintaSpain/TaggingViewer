package com.adevinta.android.taggingviewer.sample

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.adevinta.android.taggingviewer.TaggingViewer

class MergeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_merge)

    findViewById<SwitchCompat>(R.id.overlayEnabledSwitch).let { switch ->
      switch.isChecked = TaggingViewer.isOverlayEnabled()
      switch.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
          TaggingViewer.enableOverlay()
        } else {
          TaggingViewer.disableOverlay()
        }
      }
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
  }

  override fun onStart() {
    super.onStart()
    TaggingViewer.tagScreen("Merge activity")
    findViewById<SwitchCompat>(R.id.overlayEnabledSwitch).isChecked = TaggingViewer.isOverlayEnabled()
  }
}
