package com.segment.analytics.destinations.survicate.testapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.segment.analytics.kotlin.core.Analytics

class MainActivity : AppCompatActivity() {

    private val analytics: Analytics get() = (application as MainApp).analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupScreenLogButton()
    }

    private fun setupScreenLogButton() {
        findViewById<Button>(R.id.btnLogScreen).setOnClickListener {
            analytics.screen("test")
        }
    }
}
