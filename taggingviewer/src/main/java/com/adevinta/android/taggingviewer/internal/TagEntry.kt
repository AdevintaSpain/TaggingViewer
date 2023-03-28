package com.adevinta.android.taggingviewer.internal

sealed class TagEntry(
  open val name: String,
  open val timestamp: Long,
  open val version: Int?,
) {
  sealed class SeparatorEntry(
    override val name: String = "",
    override val timestamp: Long = System.currentTimeMillis()
  ) : TagEntry(name, timestamp, version = null) {
    object Space : SeparatorEntry()
    data class Named(override val name: String) : SeparatorEntry(name = name)
  }

  sealed class ItemEntry(
    override val name: String,
    open val details: Map<String, String>,
    override val timestamp: Long,
    override val version: Int?,
  ) : TagEntry(name, timestamp, version) {
    data class Click(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap(),
      override val version: Int? = null,
    ) : ItemEntry(name, details, timestamp, version)

    data class Screen(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap(),
      override val version: Int? = null,
    ) : ItemEntry(name, details, timestamp, version)

    data class Event(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap(),
      override val version: Int? = null,
    ) : ItemEntry(name, details, timestamp, version)

    data class UserAttribute(
      override val name: String,
      override val timestamp: Long = System.currentTimeMillis(),
      override val details: Map<String, String> = emptyMap(),
      override val version: Int? = null,
    ) : ItemEntry(name, details, timestamp, version)
  }
}
