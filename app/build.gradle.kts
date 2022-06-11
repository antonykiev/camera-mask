import org.jetbrains.kotlin.ir.backend.js.compile
import utils.*

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Base.currentSDK

    defaultConfig {
        minSdk = Base.minSDK
        targetSdk = Base.currentSDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {

    implementation (Dependencies.Ktx.core)
    implementation (Dependencies.Androidx.appCompat)
    implementation (Dependencies.Androidx.material)
    implementation (Dependencies.Androidx.constraintLayout)

    implementation (Dependencies.Ktx.core)
    implementation (Dependencies.Ktx.activity)
    implementation (Dependencies.Ktx.fragment)

    implementation (Dependencies.Binding.delegate)

    implementation ("com.github.permissions-dispatcher:ktx:1.1.4")

}