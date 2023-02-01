package com.adevinta.android.taggingviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.taggingviewer.databinding.TaggingActivityDetailedBinding
import com.adevinta.android.taggingviewer.filter.TaggingViewerFilterListBottomSheet
import com.adevinta.android.taggingviewer.internal.TagEntry
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailedTaggingActivity : AppCompatActivity() {

  private val viewModel: DetailTaggingViewModel by viewModels()

  private val adapter: DetailTaggingAdapter = DetailTaggingAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = TaggingActivityDetailedBinding.inflate(layoutInflater)
    setContentView(binding.root)
    title = "Tagging Viewer"

    binding.detailedTaggingListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.detailedTaggingListView.adapter = adapter

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.RESUMED) {
        launch {
          viewModel.flow.collect { entries ->
            adapter.entries = entries
            (this@DetailedTaggingActivity as MenuHost).invalidateMenu()
          }
        }

        launch {
          viewModel.filterShown.collect { filters ->
            filters?.let { showFilterList(it) }
          }
        }
      }
    }

    addMenuProvider(object : MenuProvider {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_detailed_tagging, menu)
      }

      override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)

        menu.findItem(R.id.menu_filter).isEnabled = adapter.entries.isNotEmpty()
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
          R.id.menu_remove -> {
            viewModel.clearAll()
            adapter.entries = listOf()
            true
          }
          R.id.menu_filter -> {
            viewModel.onShowFilters()
            true
          }
          else -> false
        }
      }
    }, this)


    viewModel.onInit(this)
  }

  private fun showFilterList(filters: Map<String, Boolean>) {
    viewModel.onFiltersShown()
    TaggingViewerFilterListBottomSheet.show(
      fm = supportFragmentManager,
      itemTypes = filters,
      onTypeVisibilityChanged = { type, visible ->
        viewModel.onFilterUpdated(type, visible)
      },
    )
  }
}
