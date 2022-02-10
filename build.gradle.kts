// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.TOOLS_BUILD_GRADLE)
        classpath(Dependencies.KOTLIN_GRADLE_PLUGIN)
        classpath(Dependencies.DAGGER_HILT_GRADLE_PLUGIN)
        classpath(Dependencies.NAVIGATION_SAFE_ARGS)
        classpath(Dependencies.GOOGLE_SERVICES)
        classpath(Dependencies.FIREBASE_CRASHLYTICS_GRADLE)
        // ktlint
        classpath(Dependencies.KTLINT_GRADLE)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
plugins {
    id(Plugins.KTLINT) version Versions.KTLINT_VERSION
}
tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
    register("installGitHook", Copy::class) {
        from(File(rootProject.rootDir, "scripts/pre-commit"))
        into(File(rootProject.rootDir, ".git/hooks"))
        fileMode = 777
    }
}
