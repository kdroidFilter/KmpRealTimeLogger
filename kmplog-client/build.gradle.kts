import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)

}

val appVersion = "0.5.0"
val appPackageName = "com.kdroid.kmplog.client"

group = appPackageName
version = appVersion

android {
    namespace = "com.kdroid.kmplog.client"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.kdroid.kmplog.client"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = appVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }

}

dependencies {
    debugImplementation(compose.uiTooling)
}

kotlin {
    jvm("desktop")
  //  jvmToolchain(17)

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(project(":kmplog-core"))
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            //Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)


            //Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
            implementation(libs.koin.compose)

            //Ktor client
            implementation(libs.ktor.client.websockets)
            implementation(libs.ktor.ktor.client.core)

            implementation(libs.kotlinx.serialization.protobuf)

            //Navigation
            implementation(libs.navigation.compose)

            //Data store
            implementation(libs.multiplatform.settings.no.arg)

            //UI
            implementation(libs.composables.core)
            implementation("io.github.dokar3:sonner:0.3.9") //from my fork, need to upload it on mavenlocal

        }


        val desktopMain by getting {
            dependsOn(commonMain.get())
            dependencies {
                implementation(compose.desktop.currentOs) {
                    exclude(group = "org.jetbrains.compose.material")
                }
                implementation(libs.slf4j.simple)
                implementation(libs.jsystemthemedetector)
                implementation(libs.kotlinx.coroutines.swing)

                //Jewel
                implementation(libs.jewel)
                implementation(libs.jewel.decorated)

                implementation(libs.jewel.foundation)
                    //  implementation(libs.jewel.icons)
                //
                implementation(libs.jmdns)
                implementation(libs.ktor.client.cio)
            }
        }
        val androidWasmMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ui.tiles)
            }
        }

        val androidMain by getting {
            dependsOn(commonMain.get())
            dependsOn(androidWasmMain)
            dependencies {
                implementation(libs.activity.ktx)
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.androidx.compose)
                implementation(libs.koin.androidx.compose.navigation)
                implementation(libs.ktor.client.cio)
                implementation(libs.core.splashscreen)

            }
        }
        val wasmJsMain by getting {
            dependsOn(commonMain.get())
            dependsOn(androidWasmMain)
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(libs.ktor.client.js)

            }
        }

    }

}


compose.desktop {
    application {
        mainClass = "com.kdroid.kmplog.client.MainKt"
        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
        }
        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Kmp RealTime Logger"
            packageVersion = version.toString()
            description = "A client application for real-time display and monitoring of logs across multiple platforms"
            copyright = "© 2024 KdroidFilter. All rights reserved."
            licenseFile.set(rootProject.file("LICENSE"))
            windows {
                dirChooser = true
                perUserInstall = true
                iconFile.set(rootProject.file("assets/icon.ico"))
                menuGroup = "start-menu-group"
            }
            linux {
                iconFile.set(rootProject.file("assets/icon.png"))

            }
        }
    }
}
