plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.template_project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.template_project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        isCoreLibraryDesugaringEnabled = true

    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.cardview)
    implementation(libs.glide)
    implementation(libs.circleindicator)
    implementation(libs.recyclerview)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation ("com.google.android.material:material:1.1.0")
    implementation ("io.github.chaosleung:pinview:1.4.4")
    implementation ("com.jaredrummler:material-spinner:1.3.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
}