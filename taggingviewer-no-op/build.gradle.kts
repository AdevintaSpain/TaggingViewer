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
}

dependencies {
  implementation(libs.androidx.appcompat)
}
