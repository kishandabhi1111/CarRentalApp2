buildscript {
    dependencies {
        // 🔥 Google Services plugin (Firebase REQUIRED)
        classpath("com.google.gms:google-services:4.4.2")
    }
}

plugins {
    // Android Gradle Plugin via Version Catalog
    alias(libs.plugins.android.application) apply false
}
