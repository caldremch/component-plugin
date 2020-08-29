import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.0"
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


repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}