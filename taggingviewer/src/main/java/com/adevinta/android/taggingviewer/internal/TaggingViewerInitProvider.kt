package com.adevinta.android.taggingviewer.internal

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.net.Uri
import androidx.annotation.Nullable
import com.adevinta.android.taggingviewer.BuildConfig
import com.adevinta.android.taggingviewer.TaggingViewer

/**
 * Auto-initialize the library.
 * Reference: https://medium.com/@andretietz/auto-initialize-your-android-library-2349daf06920
 */
class TaggingViewerInitProvider : ContentProvider() {
  override fun onCreate(): Boolean {
    TaggingViewer.init(context!!)
    return true
  }

  override fun attachInfo(context: Context, providerInfo: ProviderInfo?) {
    if (providerInfo == null) {
      throw NullPointerException("YourLibraryInitProvider ProviderInfo cannot be null.")
    }
    // So if the authorities equal the library internal ones, the developer forgot to set his applicationId
    if (BuildConfig.LIBRARY_PACKAGE_NAME + ".taggingviewerinitprovider" == providerInfo.authority) {
      throw IllegalStateException(
        "Incorrect provider authority in manifest. Most likely due to a " + "missing applicationId variable in application\'s build.gradle."
      )
    }
    super.attachInfo(context, providerInfo)
  }

  @Nullable
  override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?) = null

  @Nullable
  override fun getType(uri: Uri) = null

  @Nullable
  override fun insert(uri: Uri, values: ContentValues?) = null

  override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

  override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) = 0
}
