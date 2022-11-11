import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    `kotlin-dsl`
    id ("java-gradle-plugin")
}

repositories {
    maven {
        setUrl("../repo")
    }
    google()
    mavenCentral()

}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
val asm_version = "9.2"
dependencies {
    implementation("com.android.tools.build:gradle:8.0.0-alpha07")
    implementation("com.android.tools:common:30.3.1")
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
    implementation("com.caldremch.android:plugin-api:1.0.0")
    implementation("io.github.caldremch:core-logger:1.0.7-local")
    api ("org.ow2.asm:asm:$asm_version")
    api ("org.ow2.asm:asm-analysis:$asm_version")
    api ("org.ow2.asm:asm-commons:$asm_version")
    api ("org.ow2.asm:asm-tree:$asm_version")
    api ("org.ow2.asm:asm-util:$asm_version")
}

gradlePlugin {
    plugins {
        create("myPlugins") {
            id = "component.android"
            implementationClass = "ComponentPlugin"
        }
    }
}