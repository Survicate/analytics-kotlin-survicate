val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = getVersionName()

plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

nexusPublishing {
    repositories {
        sonatype()
    }
}

fun getVersionName() =
    if (hasProperty("release"))
        VERSION_NAME
    else
        "$VERSION_NAME-SNAPSHOT"
