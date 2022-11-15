plugins {
  id("com.android.library")
  kotlin("android")
}

//apply(from = "../config/android-quality.gradle")

ext["PUBLISH_ARTIFACT_ID"] = "tagging-viewer-no-op"

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")


android {
  compileSdk = 33

  defaultConfig {
    targetSdk = 33
    minSdk = 23
  }
}

dependencies {
  implementation(libs.androidx.appcompat)
}
