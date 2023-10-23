package com.segment.analytics.destinations.survicate.testapp

import android.app.Application
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.Analytics
import com.segment.analytics.kotlin.destinations.survicate.SurvicateDestination

class MainApp : Application() {

    lateinit var analytics: Analytics
        private set

    override fun onCreate() {
        super.onCreate()
        initializeSegmentAnalytics()
    }

    private fun initializeSegmentAnalytics() {
        val segmentKey = getString(R.string.segment_key)
        if (segmentKey.isBlank()) {
            throw Exception("The segment.key property is missing in local.properties")
        }

        analytics = Analytics(segmentKey, applicationContext) {
            flushAt = 3
            flushInterval = 10
        }
        analytics.add(SurvicateDestination(applicationContext))
    }
}
