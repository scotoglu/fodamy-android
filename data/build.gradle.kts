plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.KTLINT)
}

val BASE_URL: String by project
val DBNAME: String by project

android {
    compileSdk = ConfigData.COMPILE_SDK_VERSION

    defaultConfig {

        minSdk = ConfigData.MIN_SDK_VERSION
        targetSdk = ConfigData.TARGET_SDK_VERSION
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            buildConfigField("String", "BASE_URL", BASE_URL)
            buildConfigField("String", "DBNAME", DBNAME)
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
}

dependencies {
    implementation(project(Modules.DOMAIN))
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.EXT_JUNIT)

    implementation(Dependencies.RETROFIT2)
    implementation(Dependencies.GSON)
    implementation(Dependencies.GSON_CONVERTER)

    implementation(Dependencies.PAGING_RUNTIME)
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_COMPILER)
    implementation(Dependencies.DATASTORE_PREFERENCES)
    implementation(Dependencies.LOGGING_INTERCEPTOR)

    implementation(Dependencies.ROOM_RUNTIME)
    annotationProcessor(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.ROOM_EXTENSIONS)
}
