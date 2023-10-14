package com.andresestevez.soreh.buildsrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:8.1.0"
    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val accompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:0.31.3-beta"
    const val material = "com.google.android.material:material:1.9.0"
    const val coilCompose = "io.coil-kt:coil-compose:2.3.0"
    const val guava = "com.google.guava:guava:29.0-android"
    const val junit = "junit:junit:4.13.2"
    const val turbine = "app.cash.turbine:turbine:1.0.0"
    const val spinkit = "com.github.commandiron:ComposeLoading:1.0.4"
    const val placeholder = "com.google.accompanist:accompanist-placeholder-material:0.30.1"
    const val splashscreen = "androidx.core:core-splashscreen:1.0.1"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.12"

    object JavaX {
        const val inject = "javax.inject:javax.inject:1"
    }

    object Kotlin {
        private const val version = "1.8.20"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

        object Coroutines {
            private const val version = "1.6.4"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Hilt {
        private const val version = "2.46"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val androidTesting = "com.google.dagger:hilt-android-testing:$version"
    }

    object AndroidX {
        const val material3 = "androidx.compose.material3:material3:1.1.2"
        const val coreKtx = "androidx.core:core-ktx:1.10.1"
        const val activityCompose = "androidx.activity:activity-compose:1.7.1"
        const val navigationCompose = "androidx.navigation:navigation-compose:2.5.3"
        const val hiltNavigationCompose= "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val hiltWork = "androidx.hilt:hilt-work:1.0.0"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
        const val palette = "androidx.palette:palette:1.0.0"
        const val coreTesting ="androidx.arch.core:core-testing:2.1.0"
        const val profileinstaller = "androidx.profileinstaller:profileinstaller:1.3.1"

        object Work {
            private const val version = "2.8.1"

            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
            const val testing = "androidx.work:work-testing:$version"
        }

        object Compose {
            private const val version = "1.4.3"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiGraphics = "androidx.compose.ui:ui-graphics:$version"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val uiViewbinding = "androidx.compose.ui:ui-viewbinding:$version"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"

        }

        object Room {
            private const val version = "2.6.0-alpha01"
            const val roomRuntime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            //optional - Kotlin Extensions and Coroutines support for Room
            const val roomKtx = "androidx.room:room-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.6.1"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            // collectAsStateWithLifecycle
            const val runtimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:$version"
            // asFlow()
            const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        }

        object Tv {
            private const val version = "1.0.0-alpha07"
            const val foundation = "androidx.tv:tv-foundation:$version"
            const val material = "androidx.tv:tv-material:$version"
        }


        object Test {
            const val core = "androidx.test:core-ktx:1.5.0"
            const val runner = "androidx.test:runner:1.5.0"
            const val rules = "androidx.test:rules:1.5.0"
            const val junit = "androidx.test.ext:junit-ktx:1.1.5"
            const val espresso = "androidx.test.espresso:espresso-core:3.5.1"

            object Compose {
                private const val version = "1.4.3"
                const val junit4 = "androidx.compose.ui:ui-test-junit4:$version"
                const val tooling = "androidx.compose.ui:ui-tooling:$version"
                const val manifest = "androidx.compose.ui:ui-test-manifest:$version"
            }

        }
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Okhttp3 {
        private const val version = "4.11.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$version"
    }

    object Mockito {
        const val kotlin = "org.mockito.kotlin:mockito-kotlin:5.1.0"
        const val inline = "org.mockito:mockito-inline:5.2.0"
    }
}