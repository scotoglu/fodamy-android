/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 11:38
 */
object Dependencies {

    const val ANDROIDX_CORE = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val LEGACY_SUPPORT ="androidx.legacy:legacy-support-v4:${Versions.ANDROIDX_LEGACY_SUPPORT}"

    const val DAGGER_HILT = "com.google.dagger:hilt-android:${Versions.DAGGER_HILT}"
    const val DAGGER_HILT_COMPILER ="com.google.dagger:hilt-android-compiler:${Versions.DAGGER_HILT}"

    const val RETROFIT2="com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val GSON_CONVERTER="com.squareup.retrofit2:converter-gson:${Versions.GSON_CONVERTER}"
    const val GSON="com.google.code.gson:gson:${Versions.GSON}"
    const val LOGGING_INTERCEPTOR="com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR}"

    const val GLIDE="com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_COMPILER="com.github.bumptech.glide:compiler:${Versions.GLIDE_COMPILER}"

    const val LIFECYCLE_LIVEDATA="androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_COROUTINE}"
    const val LIFECYCLE_VIEWMODEL="androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_COROUTINE}"
    const val LIFECYCLE_RUNTIME="androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_COROUTINE}"

    const val PAGING_RUNTIME="androidx.paging:paging-runtime-ktx:${Versions.PAGING}"

    const val DATASTORE_PREFERENCES="androidx.datastore:datastore-preferences:${Versions.DATASTORE_PREFERENCES}"

    const val NAVIGATION_COMPONENT="androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_COMPONENT}"
    const val NAVIGATION_UI="androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_COMPONENT}"

    const val CONTRAINT_LAYOUT="androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val FRAGMENT_KTX="androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val RECYCLERVIEW="androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"
    const val MATERIAL_DESIGN="com.google.android.material:material:${Versions.MATERIAL_DESIGN}"
    const val SAVEDSTATE="androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.VIEWMODEL_SAVEDSTATE}"

    const val DOTSINDICATOR="com.tbuonomo:dotsindicator:${Versions.DOTSINDICATOR}"
    const val CIRCLE_IMAGEVIEW="de.hdodenhof:circleimageview:${Versions.CIRCLE_IMAGEVIEW}"

    //classpath
    const val TOOLS_BUILD_GRADLE="com.android.tools.build:gradle:${Versions.TOOLS_BUILD_GRADLE}"
    const val KOTLIN_GRADLE_PLUGIN="org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_GRADLE_PLUGIN}"
    const val DAGGER_HILT_GRADLE_PLUGIN="com.google.dagger:hilt-android-gradle-plugin:${Versions.DAGGER_HILT}"
    const val NAVIGATION_SAFE_ARGS="androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_COMPONENT}"
    const val KTLINT_GRADLE="org.jlleitschuh.gradle:ktlint-gradle:${Versions.KTLINT_VERSION}"
    //tests
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val EXT_JUNIT = "androidx.test.ext:junit:${Versions.EXT_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}