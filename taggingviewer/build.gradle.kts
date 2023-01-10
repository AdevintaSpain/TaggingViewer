plugins {
  id("com.android.library")
  kotlin("android")
}

//apply(from = "../config/android-quality.gradle")

ext["PUBLISH_ARTIFACT_ID"] = "tagging-viewer"

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")

android {
  compileSdk = 33

  defaultConfig {
    targetSdk = 33
    minSdk = 21
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
}

