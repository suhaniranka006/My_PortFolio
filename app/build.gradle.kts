plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.my_portfolio"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.my_portfolio"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
// In app/build.gradle.kts


        // --- Your Version Catalog Dependencies (These are CORRECT) ---
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom)) // The BOM is the manager
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3) // This is the correct way

        // --- Testing Dependencies (These are CORRECT) ---
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)

        // --- Other Dependencies (with corrected versions) ---
        implementation("androidx.navigation:navigation-compose:2.7.7") // Corrected to latest stable
        implementation("io.coil-kt:coil-compose:2.6.0") // Corrected to latest stable
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0") // Corrected to latest stable
        implementation("androidx.compose.material:material-icons-extended") // NO version number needed



        // --- DELETE THESE CONFLICTING LINES ---
        // implementation("androidx.compose.material3:material3:1.3.0") // DELETE THIS DUPLICATE
        // implementation("androidx.compose.material:material-icons-extended:1.6.0") // DELETE THIS (the one above is correct)
    }
