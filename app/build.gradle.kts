plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.cinema2"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.cinema2"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "TMDB_KEY", "\"b5887381d15507d75e7999651f05716e\"")
    }

    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
// RecyclerView - pour la liste de films
    implementation("androidx.recyclerview:recyclerview:1.3.2")
// CardView - pour les cartes films
    implementation("androidx.cardview:cardview:1.0.0")
// Glide - pour charger les affiches depuis internet
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
}