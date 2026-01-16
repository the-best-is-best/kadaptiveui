import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)

//    id("org.ajoberstar.git-publish") version "5.1.2"
    alias(libs.plugins.maven.publish)
    id("signing")


}

tasks.withType<PublishToMavenRepository> {
    val isMac = getCurrentOperatingSystem().isMacOsX
    onlyIf {
        isMac.also {
            if (!isMac) logger.error(
                """
                    Publishing the library requires macOS to be able to generate iOS artifacts.
                    Run the task on a mac or use the project GitHub workflows for publication and release.
                """
            )
        }
    }
}


extra["packageNameSpace"] = "io.github.tbib.kadaptiveui"
extra["groupId"] = "io.github.the-best-is-best"
extra["artifactId"] = "kadaptiveui"
extra["version"] = "1.2.1"
extra["packageName"] = "KAdaptiveUI"
extra["packageUrl"] = "https://github.com/the-best-is-best/kadaptiveui"
extra["packageDescription"] =
    "KAdaptiveUI is a Kotlin Multiplatform library that provides a set of adaptive UI components for Android and iOS using Jetpack Compose and UIKit.  \n" +
            "It helps developers write once and run adaptive UI for both platforms with platform-specific look and feel."
extra["system"] = "GITHUB"
extra["issueUrl"] = "https://github.com/the-best-is-best/kadaptiveui/issues"
extra["connectionGit"] = "https://github.com/the-best-is-best/kadaptiveui.git"

extra["developerName"] = "Michelle Raouf"
extra["developerNameId"] = "MichelleRaouf"
extra["developerEmail"] = "eng.michelle.raouf@gmail.com"


mavenPublishing {
    coordinates(
        extra["groupId"].toString(),
        extra["artifactId"].toString(),
        extra["version"].toString()
    )

    publishToMavenCentral(true)
    signAllPublications()

    pom {
        name.set(extra["packageName"].toString())
        description.set(extra["packageDescription"].toString())
        url.set(extra["packageUrl"].toString())
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://opensource.org/licenses/Apache-2.0")
            }
        }
        issueManagement {
            system.set(extra["system"].toString())
            url.set(extra["issueUrl"].toString())
        }
        scm {
            connection.set(extra["connectionGit"].toString())
            url.set(extra["packageUrl"].toString())
        }
        developers {
            developer {
                id.set(extra["developerNameId"].toString())
                name.set(extra["developerName"].toString())
                email.set(extra["developerEmail"].toString())
            }
        }
    }

}


signing {
    useGpgCmd()
    sign(publishing.publications)
}


val nameSpace = extra["packageNameSpace"].toString()

kotlin {
    androidLibrary {
        namespace = nameSpace
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder { }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "KAdaptiveUIKit"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = xcfName
        }
    }


    jvm()

    js()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()

    sourceSets {
        all {
            languageSettings {
                // Opt-in once, applies to all targets
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi")
                // تضيف أي API تانية انت محتاجها
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.runtime)
                implementation(libs.foundation)
                implementation(libs.material3)
                implementation(libs.kotlinx.datetime)

            }
        }

//        val commonTest by getting {
//            dependencies {
//                implementation(libs.kotlin.test)
//            }
//        }

        val materialMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.material3.adaptive.navigation.suite) // Shared Compose Material layer
                implementation(libs.material.icons.extended)
            }
        }

        val androidMain by getting {
            dependsOn(materialMain) // Android uses Material through materialMain
        }

        val jsMain by getting {
            dependsOn(materialMain) // JS uses Material through materialMain
        }

        val jvmMain by getting {
            dependsOn(materialMain) // JVM uses Material through materialMain
            dependencies {
                implementation(compose.desktop.currentOs) // Desktop Compose
            }
        }

        val wasmJsMain by getting {
            dependsOn(materialMain) // WASM uses Material through materialMain
        }


        val appleMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }

        val iosX64Main by getting {
            dependsOn(appleMain)
        }
        val iosArm64Main by getting {
            dependsOn(appleMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(appleMain)
        }

//        getByName("androidDeviceTest") {
//            dependencies {
//                implementation(libs.androidx.runner)
//                implementation(libs.androidx.core)
//                implementation(libs.androidx.testExt.junit)
//            }
//        }
    }
}