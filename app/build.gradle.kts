plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    // If you're using KSP (recommended for Kotlin projects)
    alias(libs.plugins.ksp)
}
//val mockitoAgent by configurations.creating {
//    isTransitive = false
//}
//tasks.withType<Test>().configureEach {
//    jvmArgs("-javaagent:${mockitoAgent.asPath}")
//}
android {
    namespace = "com.kamath.newsfeed"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kamath.newsfeed"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    //exception handling
    implementation(libs.arrow.core)

    //timber
    implementation(libs.timber)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit) // Core JUnit testing framework. [1]
    testImplementation(libs.kotlinx.coroutines.test.v181) // For testing coroutines and using TestDispatchers. [2]
    testImplementation(libs.mockito.kotlin) // Mocking library for Kotlin. [3, 4]
    testImplementation(libs.mockito.inline) // Needed to mock final classes and methods, common in Kotlin. [3, 4]
    testImplementation(libs.androidx.core.testing) // For InstantTaskExecutorRule to test components using main looper. [5]
    testImplementation(libs.turbine)
}