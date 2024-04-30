import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.safeagrs)
    alias(libs.plugins.room)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlitycs)
    id("kotlin-parcelize")
}

android {
    namespace = "com.kuro.movie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kuro.movie"
        minSdk = 24
        targetSdk = 28
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }

    val apikey: String by localProperties
    val apiKeyValue = localProperties.getProperty("API_KEY") ?: ""


    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"$apiKeyValue\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"$apiKeyValue\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    ksp {
        arg("room.generateKotlin", "true")
    }

    hilt {
        enableAggregatingTask = true
    }


    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation(libs.retrofit)

    // OkHttp Caching
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    // Gson Convertor
    implementation(libs.converter.gson)

    // RxJava Adapter
    implementation(libs.retrofit.adapter.rxjava2)

    // Gson
    implementation(libs.gson)

    // Glide
    implementation(libs.glide)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.rxjava2)


    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    //Recyclerview
    implementation(libs.androidx.recyclerview)
    implementation(libs.sparrow007.carouselrecyclerview)

    implementation(libs.androidx.cardview)

    // coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Room Database
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // optional - RxJava support for Room
    implementation(libs.androidx.room.rxjava2)

    // Rxjava
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.kotlinx.metadata.jvm)

    // Shimmer
    implementation(libs.shimmer)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth.ktx)

    implementation(libs.firebase.firestore)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.svg)
}