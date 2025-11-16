plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.project1"
    compileSdk {
        version = release(36)
    }

    buildFeatures{
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.project1"
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
// Gson converter for JSON serialization/deserialization
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
// (Optional) OkHttp logging interceptor for network request logging
    implementation("com.squareup.okhttp3:logging-interceptor:5.3.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
// LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.4")
    //noinspection UseTomlInstead





    //glide
    implementation("com.github.bumptech.glide:glide:5.0.5")
}