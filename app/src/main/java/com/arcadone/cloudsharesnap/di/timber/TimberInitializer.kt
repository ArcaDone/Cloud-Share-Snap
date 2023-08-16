package com.arcadone.cloudsharesnap.di.timber

import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject internal constructor() {
    init {
        Timber.plant(Timber.DebugTree())
    }
}