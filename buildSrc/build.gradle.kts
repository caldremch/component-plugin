import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.31"
}


buildscript {
    repositories {
        mavenLocal()
        jcenter()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
    }

}
val plugin_api_repo_uri =project.rootDir.absolutePath.replace("/buildSrc", "") + File.separator + "repo"
System.out.println("plugin_api_repo_uri=${plugin_api_repo_uri}")
repositories {
    maven { url = uri(plugin_api_repo_uri) }
    mavenLocal()
    mavenCentral()
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.caldremch.android:plugin-api:1.0.2")
//    implementation(project(":plugin-api"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}