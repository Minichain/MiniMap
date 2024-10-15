pluginManagement {
  repositories {
    google()
    maven { url = java.net.URI.create("https://plugins.gradle.org/m2/" ) }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "AndroidMapApp"
include(":app")
 