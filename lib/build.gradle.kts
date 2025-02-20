plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
}

ext.set("lib_version", "6.0.0")

android {
    namespace = "com.segment.analytics.kotlin.destinations.survicate"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // documentation
    tasks.dokkaHtml.configure {
        outputDirectory.set(buildDir.resolve("dokka"))
    }

    publishing {
        singleVariant("release")
    }
}

dependencies {
    implementation(libs.segment.analytics)
}

// Partner Dependencies
dependencies {
    implementation(libs.serialization.json)
    implementation(libs.survicate.sdk)
}

// Test Dependencies
dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.mockk)

    // Add JUnit4 legacy dependencies.
    testRuntimeOnly(libs.junit.vintage.engine)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlin.reflect)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

apply(from = "s3.publish.gradle")
