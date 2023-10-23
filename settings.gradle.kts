pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://repo.survicate.com")
    }
}

rootProject.name = "analytics-kotlin-survicate"
include(":lib")
includeBuild("publishing-plugins")
include(":testapp")
