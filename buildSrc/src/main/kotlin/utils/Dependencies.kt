package utils

object Dependencies {
    const val kotlin                = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinSerialization   = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"

    object Androidx {
        const val appCompat         = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val material          = "com.google.android.material:material:${Versions.material}"
        const val constraintLayout  = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    }

    object Ktx {
        const val core              = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val fragment          = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
        const val activity          = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    }

    object LifeCycle {
        const val viewModel         = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val runtime           = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val livedata          = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

        const val viewModelCompose  = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    }

    object DI {
        object Koin {
            const val koin = "io.insert-koin:koin-android:${Versions.koin}"
        }

        object Dagger {
            const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
            const val android = "com.google.dagger:dagger-android:${Versions.dagger}"
            const val support = "com.google.dagger:dagger-android-support:${Versions.dagger}"

            const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
            const val processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
        }

        object Hilt {

            const val hilt = "com.google.dagger:hilt-android:2.38.1"
            const val compilerAndroid = "com.google.dagger:hilt-android-compiler:2.37"
            const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
            const val compiler = "androidx.hilt:hilt-compiler:1.0.0"
        }
    }

    object Room {
        const val room              = "androidx.room:room-runtime:${Versions.room}"
        const val compiler          = "androidx.room:room-compiler:${Versions.room}"
        const val ktx               = "androidx.room:room-ktx:${Versions.room}"
    }

    object Retrofit {
        const val retrofit          = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter     = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val okHttp            = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val interceptor       = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    }

    object Ktor {
        const val core              = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val android           = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val serialization     = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val logging           = "io.ktor:ktor-client-logging:$${Versions.ktor}"
    }

    object Compose {

    }

    object Coroutines {
        const val core              = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android           = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Images {
        const val glide             = "com.github.bumptech.glide:glide:${Versions.glide}"

    }

    object Navigation {
        const val navigationRuntimeKtx  = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUiKtx       = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Binding {
        const val delegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.3"
    }

    object Mapper {
        const val mapStruct         = "org.mapstruct:mapstruct:${Versions.mapStruct}"
        const val mapStructKapt     = "org.mapstruct:mapstruct-processor:${Versions.mapStruct}"
    }

    object Debugging {
        const val timber            = "com.jakewharton.timber:timber:${Versions.timber}"
    }


}