package com.adevinta.android.taggingviewer.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adevinta.android.taggingviewer.databinding.TaggingViewFilterListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaggingViewerFilterListBottomSheet(
  private val itemTypes: List<String>,
  private val onTypeVisibilityChanged: (String, Boolean) -> Unit,
) : BottomSheetDialogFragment() {

  private var binding: TaggingViewFilterListBinding? = null
  private val adapter: TaggingViewFilterAdapter = TaggingViewFilterAdapter(
    onTypeVisibilityChanged = onTypeVisibilityChanged
  )

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    val binding = TaggingViewFilterListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding = TaggingViewFilterListBinding.bind(view)

    binding?.types?.layoutManager = LinearLayoutManager(requireContext())
    binding?.types?.adapter = adapter

    showTypes(itemTypes)
  }

  private fun showTypes(itemTypes: List<String>) {
    adapter.items = itemTypes
  }

  companion object {
    fun show(
      fm: FragmentManager,
      itemTypes: List<String>,
      onTypeVisibilityChanged: (String, Boolean) -> Unit,
    ): TaggingViewerFilterListBottomSheet {
      val sheet = TaggingViewerFilterListBottomSheet(
        itemTypes, onTypeVisibilityChanged
      )
      sheet.show(fm, this::class.simpleName)
      return sheet
    }
  }
}
