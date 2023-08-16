package com.arcadone.cloudsharesnap

import android.app.Application
import com.arcadone.cloudsharesnap.di.timber.TimberInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    internal lateinit var timberInitializer: TimberInitializer

}