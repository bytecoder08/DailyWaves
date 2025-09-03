package com.bytecoder.dailywaves

import android.app.Application
import com.google.android.material.color.DynamicColors

class DailyWavesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
