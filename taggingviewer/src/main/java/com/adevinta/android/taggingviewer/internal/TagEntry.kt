package com.adevinta.android.taggingviewer.internal

sealed class TagEntry(
  open val name: String,
  open val timestamp: Long
) {
  sealed class SeparatorEntry(
    override val name: String = "",
    override val timestamp: Long = System.currentTimeMillis()
  ) : TagEntry(name, timestamp) {
    object Space : SeparatorEntry()
    data class Named(override val name: String) : SeparatorEntry(name = name)
  }

  sealed class ItemEntry(
    override val name: String,
    open val details: Map<String, String>,
    override val timestamp: Long
  ) : TagEntry(name, timestamp) {
    data class Click(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap()
    ) : ItemEntry(name, details, timestamp)

    data class Screen(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap()
    ) : ItemEntry(name, details, timestamp)

    data class Event(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap()
    ) : ItemEntry(name, details, timestamp)

    data class UserAttribute(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap()
    ) : ItemEntry(name, details, timestamp)
  }
}
