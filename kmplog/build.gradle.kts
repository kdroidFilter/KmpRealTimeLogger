import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    alias(libs.plugins.vannitktech.maven.publish)
}

group = "com.kdroid.kmplog"
version = "0.4.7"


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
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.protobuf)

            implementation(project(":kmplog-core"))

            //Ktor
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.websockets)
            implementation(libs.ktor.server.cors)
            implementation(libs.ktor.serialization.kotlinx.protobuf)
            implementation(libs.ktor.server.content.negotiation)

        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        jvmMain.dependencies {
            implementation(libs.slf4j.simple)
        }
        nativeMain.dependencies {
        }

        val nativeJvmAndroidMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ktor.server.cio)

            }
        }

        val nativeJvmWasmMain by creating {
            dependsOn(commonMain.get())
            dependencies {
            }
        }

        val jvmAndroidMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.jmdns)

            }
        }


        val nativeMain by getting {
            dependsOn(commonMain.get())
            dependencies {

            }
        }



        androidMain {
            dependsOn(nativeJvmAndroidMain)
            dependsOn(jvmAndroidMain)
        }


        linuxX64Main {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(nativeMain)
        }
        mingwX64Main {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(nativeMain)
        }
        macosX64Main {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(nativeMain)
        }
        macosArm64Main {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(nativeMain)
        }
        iosX64Main {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(nativeMain)
        }
        iosArm64Main {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(nativeMain)
        }
        iosSimulatorArm64Main() {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(nativeMain)
        }
        wasmJsMain {
           dependsOn(nativeJvmWasmMain)
       }
        jvmMain {
            dependsOn(nativeJvmWasmMain)
            dependsOn(nativeJvmAndroidMain)
            dependsOn(jvmAndroidMain)
        }

        val nativeJvmWasmTest by creating {
            dependsOn(commonTest.get())
            dependencies {
                implementation(kotlin("test"))
            }
        }
        linuxX64Test {
            dependsOn(nativeJvmWasmTest)
        }
        mingwX64Test {
            dependsOn(nativeJvmWasmTest)
        }
        macosX64Test {
            dependsOn(nativeJvmWasmTest)
        }
        macosArm64Test {
            dependsOn(nativeJvmWasmTest)
        }
        iosX64Test {
            dependsOn(nativeJvmWasmTest)
        }
        iosArm64Test {
            dependsOn(nativeJvmWasmTest)
        }
        iosSimulatorArm64Test {
            dependsOn(nativeJvmWasmTest)
        }
        wasmJsTest {
            dependsOn(nativeJvmWasmTest)
        }

        jvmTest {
            dependsOn(nativeJvmWasmTest)
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

mavenPublishing {
    coordinates(
        groupId = "io.github.kdroidfilter",
        artifactId = "kmplog",
        version = version.toString()
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("KMPLog")
        description.set("Logging library that replicates the functionality of Android's Log library")
        inceptionYear.set("2024")
        url.set("https://github.com/kdroidFilter/KMPLog")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("kdroidfilter")
                name.set("Elyahou Hadass")
                email.set("elyahou.hadass@gmail.com")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/kdroidFilter/KMPLog ")
        }
    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    
    // Enable GPG signing for all publications
    signAllPublications()
}


task("testClasses") {}