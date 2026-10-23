plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
}

android {
    namespace = "com.example.rogosdk_lib"
    compileSdk = 36

    defaultConfig {
        // raio_sdk yêu cầu minSdk 29
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

// --- PHẦN XỬ LÝ ĐẶC BIỆT ĐỂ NHÚNG AAR GIỐNG NHƯ JAR ---
// AAR cục bộ không thể khai báo trong POM nên ta giải nén classes.jar và nhúng thẳng vào AAR của lib.
// Manifest của AAR (permission + RoSdkInitializer) đã được copy sang src/main/AndroidManifest.xml.
val extractedAarDir = layout.buildDirectory.dir("intermediates/extracted_aar_jar")
val extractAarJarTask = tasks.register<Copy>("extractAarJar") {
    val aarFile = file("libs/raio-android-sdk-261023-release.aar")
    if (aarFile.exists()) {
        from(zipTree(aarFile))
        include("classes.jar")
        into(extractedAarDir)
        rename("classes.jar", "raio_sdk-internal.jar")
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)

    // Nhúng file JAR đã giải nén từ raio_sdk AAR
    api(files(extractedAarDir.get().file("raio_sdk-internal.jar")) {
        builtBy(extractAarJarTask)
    })

    // Các dependency mà raio_sdk dùng (lấy từ Raio-Android-SDK/ro_sdk/build.gradle.kts),
    // khai báo ở đây để chúng đi vào POM, app consume không phải tự thêm.
    api(libs.rogocore.lib)
    api(libs.gson)
    // RogoCoreLib không khai báo hivemq trong POM
    api(libs.hivemq.mqtt.client)
    implementation(libs.room.runtime)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.lifecycle.process)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                val releaseComponent = components.findByName("release")
                if (releaseComponent != null) {
                    from(releaseComponent)
                }

                groupId = "com.github.xtung2404"
                artifactId = "rogo_sdk_lib"
                // Trên JitPack lấy theo tag, build local mặc định 1.0.0
                version = System.getenv("JITPACK_VERSION") ?: "1.0.0"
            }
        }
    }
}
