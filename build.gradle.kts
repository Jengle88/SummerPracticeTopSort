import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    java
}

group = "ru.etu"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.ui)
                implementation(compose.material)
                implementation(compose.foundation)
                implementation(compose.uiTooling)
                implementation(compose.runtime)
                implementation(compose.preview)
                implementation(compose.materialIconsExtended)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
                implementation("com.google.code.gson:gson:2.9.0")
            }
        }
        val jvmTest by getting

    }
}

compose.desktop {
    application {
        mainClass = "MainScreenKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SummerPracticeTopSort"
            packageVersion = "1.0.0"
        }
    }
}

/**
 * build: ./gradlew buildFullJar
 * run jar: (из папки SummerPracticeTopSort/build/libs) java -jar SummerPracticeTopSort-1.0-SNAPSHOT-all.jar
 * run: ./gradlew run
 */
tasks {
    register<Jar>("buildFullJar") {
        group = "jarBuild"
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Main-Class"] = "MainScreenKt"
        }
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        from(sourceSets.main.get().output)
    }
}
