plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.eidogs.xaudio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.eidogs.xaudio"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("src/main/java", "src/normal/java", "src/playstore/java")
            }
            res {
                srcDirs("src/main/res", "src/playstore/res")
            }
        }
    }
}

dependencies {
    implementation(project(":appthemehelper"))
    implementation(libs.gridLayout)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.palette.ktx)

    implementation(libs.androidx.mediarouter)
    //Cast Dependencies
    implementation(libs.google.play.services.cast.framework)
    //WebServer by NanoHttpd
    implementation(libs.nanohttpd)

    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.common.java8)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.google.feature.delivery)
    implementation(libs.google.play.review)

    implementation(libs.android.material)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.afollestad.material.dialogs.core)
    implementation(libs.afollestad.material.dialogs.input)
    implementation(libs.afollestad.material.dialogs.color)
    implementation(libs.afollestad.material.cab)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.glide)
    ksp(libs.glide.ksp)
    implementation(libs.glide.okhttp3.integration)

    implementation(libs.advrecyclerview)

    implementation(libs.fadingedgelayout)

    implementation(libs.keyboardvisibilityevent)
    implementation(libs.jetradarmobile.android.snowfall)

    implementation(libs.chrisbanes.insetter)

    implementation(libs.org.eclipse.egit.github.core)
    implementation(libs.jaudiotagger)
    implementation(libs.android.lab.library)
    implementation(libs.slidableactivity)
    implementation(libs.material.intro)
    implementation(libs.dhaval2404.imagepicker)
    implementation(libs.fastscroll.library)
    implementation(libs.customactivityoncrash)
    implementation(libs.tankery.circularSeekBar)
}