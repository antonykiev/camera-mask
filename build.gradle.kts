buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(utils.BuildPlugins.gradle)
        classpath(utils.BuildPlugins.kotlin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}