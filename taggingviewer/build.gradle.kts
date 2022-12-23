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
    minSdk = 23
  }
  buildFeatures {
    viewBinding = true
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
  }
}

dependencies {
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.recyclerView)
  implementation(libs.androidx.lifecycle)
  implementation(libs.androidx.material)
  implementation(platform(libs.compose.bom))
  implementation(libs.androidx.activity.compose)

  implementation(platform(libs.compose.bom))
  implementation(libs.compose.foundation.foundation)
  implementation(libs.compose.foundation.layout)
  implementation(libs.compose.material.material3)
  implementation(libs.compose.ui.ui)
  implementation(libs.compose.ui.tooling)
  implementation(libs.compose.runtime.livedata)
}

