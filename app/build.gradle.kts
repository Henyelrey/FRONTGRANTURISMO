plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt") //Agregado
    id("dagger.hilt.android.plugin") //Agregad
}

android {
    namespace = "pe.edu.upeu.granturismojpc"
    compileSdk = 35

    defaultConfig {
        applicationId = "pe.edu.upeu.granturismojpc"
        minSdk = 28
        targetSdk = 35
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //Navegacion
    implementation("androidx.navigation:navigation-compose:2.8.9")
//Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.11.0")
//Formularios
    implementation ("com.github.k0shk0sh:compose-easyforms:0.2.0")
//Agregados Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.52") //old 2.47
    kapt ("com.google.dagger:hilt-compiler:2.52") //old 2.47
//Agregado Dagger - Hilt Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") //old 1.0.0
    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.2") //old 1.0.5, 1.3.1
//implementation ("io.coil-kt:coil-compose:2.7.0") //old 2.4.0
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
//Agregado LiveData compose//implementation ("androidx.compose.ui:uitooling")
    implementation ("androidx.compose.foundation:foundation:1.3.0")
    implementation ("androidx.compose.runtime:runtime-livedata")
//App Compact para detectar modo dia noche
    val appcompat_version = "1.7.0" //old 1.6.1
    implementation("androidx.appcompat:appcompat:$appcompat_version")//Agrega do recien


    implementation ("androidx.compose.material:material-icons-extended:1.6.7")

    //Coil Compose, ideal para cargar imágenes desde URLs.
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("androidx.navigation:navigation-compose:2.8.9")
    implementation("com.mapbox.maps:android:11.11.0")
    implementation ("com.mapbox.extension:maps-compose:11.11.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.accompanist:accompanist-permissions:0.37.2")



}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:deprecation")
}
