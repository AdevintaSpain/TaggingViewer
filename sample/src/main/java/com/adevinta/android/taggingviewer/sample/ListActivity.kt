package com.adevinta.android.taggingviewer.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.taggingviewer.TaggingViewer
import com.adevinta.android.taggingviewer.TaggingViewer.tagClick
import com.adevinta.android.taggingviewer.TaggingViewer.tagScreen

class ListActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)

    findViewById<SwitchCompat>(R.id.overlayEnabledSwitch).let { switch ->
      switch.isChecked = TaggingViewer.isOverlayEnabled()
      switch.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
          TaggingViewer.enableOverlay(this)
        } else {
          TaggingViewer.disableOverlay()
        }
      }
    }

    with(findViewById<RecyclerView>(R.id.recycler)) {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@ListActivity)
      adapter = TextAdapter(this@ListActivity, FRUITS)
    }
  }

  override fun onStart() {
    super.onStart()
    tagScreen("List screen")
    findViewById<SwitchCompat>(R.id.overlayEnabledSwitch).isChecked = TaggingViewer.isOverlayEnabled()
  }

  class TextAdapter(private val activity: Activity, private val items: Array<String>) :
    RecyclerView.Adapter<TextAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): ViewHolder {
      val root = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
      val textView = root.findViewById<TextView>(android.R.id.text1)
      return ViewHolder(root, textView)
    }

    override fun onBindViewHolder(
      holder: ViewHolder,
      position: Int
    ) {
      val itemName = items[position]
      holder.textView.text = itemName
      holder.itemView.setOnClickListener {
        tagClick("$itemName clicked")
        activity.startActivity(
          Intent(activity, DetailActivity::class.java)
            .putExtra(DetailActivity.EXTRA_TEXT, "$itemName has been clicked")
            .putExtra(DetailActivity.EXTRA_ITEM_NAME, itemName)
        )
      }
    }

    override fun getItemCount() = items.size

    class ViewHolder(root: View, val textView: TextView) : RecyclerView.ViewHolder(root)
  }

  companion object {
    private val FRUITS = arrayOf(
      "Apple", "Apricot", "Avocado", "Banana", "Bilberry", "Blackberry", "Blackcurrant",
      "Blueberry", "Boysenberry", "Currant", "Cherry", "Cherimoya", "Cloudberry", "Coconut",
      "Cranberry", "Cucumber", "Custardapple", "Damson", "Date", "Dragonfruit", "Durian",
      "Elderberry", "Feijoa", "Fig", "Gojiberry", "Gooseberry", "Grape", "Raisin",
      "Grapefruit", "Guava", "Honeyberry", "Huckleberry", "Jabuticaba", "Jackfruit", "Jambul",
      "Jujube", "Juniperberry", "Kiwifruit", "Kumquat", "Lemon", "Lime", "Loquat",
      "Longan", "Lychee", "Mango", "Marionberry", "Melon", "Cantaloupe", "Honeydew",
      "Watermelon", "Miraclefruit", "Mulberry", "Nectarine", "Nance", "Olive", "Orange",
      "Bloodorange", "Clementine", "Mandarine", "Tangerine", "Papaya", "Passionfruit", "Peach",
      "Pear", "Persimmon", "Physalis", "Plantain", "Plum", "Prune(driedplum)", "Pineapple",
      "Plumcot(orPluot)", "Pomegranate", "Pomelo", "Purplemangosteen", "Quince", "Raspberry",
      "Salmonberry", "Rambutan", "Redcurrant", "Salalberry", "Salak", "Satsuma", "Starfruit",
      "Solanumquitoense", "Strawberry", "Tamarillo", "Tamarind", "Uglifruit", "Yuzu"
    )
  }
}
