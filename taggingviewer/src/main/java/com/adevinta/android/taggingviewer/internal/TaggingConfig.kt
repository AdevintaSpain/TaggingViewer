package com.adevinta.android.taggingviewer.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

internal object TaggingConfig {

  private val alphaLiveData = MutableLiveData<Float>()

  fun alphaLiveData(): LiveData<Float> = alphaLiveData

  fun setAlpha(alpha: Float) {
    alphaLiveData.postValue(alpha)
  }
}
