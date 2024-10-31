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
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}


kotlin {
    jvm("desktop")
    jvmToolchain(17)

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



        }


        val desktopMain by getting {
            dependsOn(commonMain.get())
            dependencies {
                implementation(compose.desktop.currentOs) {
                    exclude(group = "org.jetbrains.compose.material")
                }
                implementation(libs.androidx.ui.tooling.preview.desktop)
                implementation(libs.slf4j.simple)
                implementation(libs.jsystemthemedetector)
                implementation(libs.kotlinx.coroutines.swing)

                //Jewel
                implementation(libs.jewel)
                implementation(libs.jewel.decorated)
                implementation(libs.jewel.markdown.core)
                implementation(libs.jewel.markdown.intUiStandaloneStyling)
                implementation(libs.jewel.markdown.extension.autolink)
                implementation(libs.jewel.markdown.extension.gfmalerts)
                implementation(libs.jewel.foundation)
                implementation(libs.jewel.icons)
                //
                implementation(libs.jmdns)
                implementation(libs.ktor.client.cio)
                implementation(libs.androidx.material.icons.extended)

            }
        }
        val androidMain by getting {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.androidx.ui.tooling.preview.android)

                implementation(libs.activity.ktx)
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.androidx.compose)
                implementation(libs.koin.androidx.compose.navigation)
                implementation(libs.ktor.client.cio)
                implementation(libs.androidx.material.icons.extended)

            }
        }
        val wasmJsMain by getting {
            dependsOn(commonMain.get())
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
dependencies {

}


compose.desktop {
    application {
        mainClass = "com.kdroid.kmplog.client.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.kdroid.kmplog.client"
            packageVersion = appVersion
            description = "KmpLog Client"
            copyright = "© 2024 KdroidFilter. All rights reserved."
        }
    }
}
