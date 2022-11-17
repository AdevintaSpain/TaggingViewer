plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  compileSdk = 33

  defaultConfig {
    targetSdk = 33
    minSdk = 23
    applicationId = "com.adevinta.android.taggingviewer.sample"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  packagingOptions {
    resources.excludes.add("META-INF/AL2.0")
    resources.excludes.add("META-INF/LGPL2.1")
  }
}

dependencies {
  debugImplementation(project(":taggingviewer"))
  releaseImplementation(project(":taggingviewer-no-op"))

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.recyclerView)
}
