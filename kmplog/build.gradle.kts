import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    id("convention.publication")
    id("maven-publish")

}

group = "com.kdroid.kmplog"
version = "0.1.0"


kotlin {
    jvmToolchain(11)
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    js {
        browser {
            webpackTask {
                mainOutputFileName = "kmplog.js"
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kmplog"
            isStatic = true
        }
    }

    listOf(
        macosX64(),
        macosArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "kmplog"
            isStatic = true
        }
    }

    linuxX64 {
        binaries.staticLib {
            baseName = "kmplog"
        }
    }


    mingwX64 {
        binaries.staticLib {
            baseName = "kmplog"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        val nativeJvmWasmMain by creating {
            dependsOn(commonMain.get())
        }

        linuxX64Main {
            dependsOn(nativeJvmWasmMain)
        }
        mingwX64Main {
            dependsOn(nativeJvmWasmMain)
        }
        macosX64Main {
            dependsOn(nativeJvmWasmMain)
        }
        macosArm64Main {
            dependsOn(nativeJvmWasmMain)
        }
        iosX64Main {
            dependsOn(nativeJvmWasmMain)
        }
        iosArm64Main {
            dependsOn(nativeJvmWasmMain)
        }
        iosSimulatorArm64Main() {
            dependsOn(nativeJvmWasmMain)
        }
        wasmJsMain {
            dependsOn(nativeJvmWasmMain)
        }
        jvmMain {
            dependsOn(nativeJvmWasmMain)
        }
    }



    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compilerOptions.options.freeCompilerArgs.add("-Xexport-kdoc")
    }

}

android {
    namespace = "com.kdroid.kmplog"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }
}

