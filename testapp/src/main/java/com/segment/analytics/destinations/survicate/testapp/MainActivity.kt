package com.segment.analytics.destinations.survicate.testapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.Analytics
import com.segment.analytics.kotlin.destinations.survicate.SurvicateDestination

class MainActivity : AppCompatActivity() {
    private lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeSegmentAnalytics()
        setupScreenLogButton()
    }

    private fun initializeSegmentAnalytics() {
        val segmentKey = getString(R.string.segment_key)
        if (segmentKey.isBlank()) {
            throw Exception("The segment.key property is missing in local.properties")
        }

        this.analytics = Analytics(segmentKey, applicationContext) {
            flushAt = 3
            flushInterval = 10
        }
        analytics.add(SurvicateDestination(applicationContext))
    }

    private fun setupScreenLogButton() {
        findViewById<Button>(R.id.btnLogScreen).setOnClickListener {
            analytics.screen("test")
        }
    }
}
