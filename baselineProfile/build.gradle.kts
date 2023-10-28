import com.andresestevez.soreh.buildsrc.Libs

plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
    id("androidx.baselineprofile")
}

android {
    namespace = "com.andresestevez.soreh.baselineprofile"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    defaultConfig {
        minSdk = 26
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"

    testOptions.managedDevices.devices {
        create<com.android.build.api.dsl.ManagedVirtualDevice>("pixel6Api29") {
            device = "Pixel 6"
            apiLevel = 29
            systemImageSource = "aosp"
        }
    }
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    managedDevices += "pixel6Api29"
    useConnectedDevices = false
}

dependencies {
    implementation(Libs.AndroidX.Test.junit)
    implementation(Libs.AndroidX.Test.espresso)
    implementation(Libs.AndroidX.Test.uiautomator)
    implementation(Libs.AndroidX.Test.benchmarkMacroJunit4)
}