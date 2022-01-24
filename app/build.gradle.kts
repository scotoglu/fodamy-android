plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT_ANDROID)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.KTLINT)
}

android {
    compileSdk = ConfigData.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = ConfigData.APPLICATION_ID
        minSdk = ConfigData.MIN_SDK_VERSION
        targetSdk = ConfigData.TARGET_SDK_VERSION
        versionCode = ConfigData.VERSION_CODE
        versionName = ConfigData.VERSION_NAME
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(project(Modules.DATA))
    implementation(project(Modules.DOMAIN))
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.LEGACY_SUPPORT)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.EXT_JUNIT)
    androidTestImplementation(Dependencies.ESPRESSO_CORE)

    // Dagger Hilt
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_COMPILER)

    implementation(Dependencies.MATERIAL_DESIGN)

    // Retrofit
    implementation(Dependencies.RETROFIT2)
    implementation(Dependencies.GSON_CONVERTER)
    implementation(Dependencies.GSON)

    // Glide
    implementation(Dependencies.GLIDE)
    annotationProcessor(Dependencies.GLIDE_COMPILER)

    // Coroutine - Lifecycle-aware
    implementation(Dependencies.LIFECYCLE_LIVEDATA)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.LIFECYCLE_RUNTIME)

    // Paging
    implementation(Dependencies.PAGING_RUNTIME)

    // Navigation Component
    implementation(Dependencies.NAVIGATION_COMPONENT)
    implementation(Dependencies.NAVIGATION_UI)

    // ConstraintLayout 2
    implementation(Dependencies.CONTRAINT_LAYOUT)

    // Fragment ktx
    implementation(Dependencies.FRAGMENT_KTX)

    // RecyclerView
    implementation(Dependencies.RECYCLERVIEW)

    // SavedState
    implementation(Dependencies.SAVEDSTATE)
    // DotsIndicator
    implementation(Dependencies.DOTSINDICATOR)

    implementation(Dependencies.CIRCLE_IMAGEVIEW)

    implementation(Dependencies.LOTTIE_ANIMATION)
}
kapt {
    correctErrorTypes = true
}
