import groovy.lang.MissingPropertyException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.segment.analytics.destinations.survicate.testapp"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.segment.analytics.destinations.survicate.testapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localPropertiesFile = rootProject.file("local.properties")
        val properties = Properties()
        if (localPropertiesFile.exists() && localPropertiesFile.canRead()) {
            properties.load(localPropertiesFile.reader())
        }
        val segmentKey = (properties["segment.key"] as? String).orEmpty()
        resValue("string", "segment_key", segmentKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":lib")))
    implementation(libs.segment.analytics)
    implementation(libs.appcompat)
    implementation(libs.material)
}
