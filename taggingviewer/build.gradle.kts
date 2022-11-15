plugins {
  id("com.android.library")
  kotlin("android")
}

android {
  compileSdk = 33

  defaultConfig {
    targetSdk = 33
    minSdk = 23
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.recyclerView)
  implementation(libs.androidx.lifecycle)
  implementation(libs.androidx.material)

  implementation(libs.network.okhttp)
  implementation(libs.network.gson)
}

