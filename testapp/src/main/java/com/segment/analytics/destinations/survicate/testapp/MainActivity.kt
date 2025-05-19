package com.segment.analytics.destinations.survicate.testapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.segment.analytics.kotlin.core.Analytics

class MainActivity : AppCompatActivity() {

    private val analytics: Analytics get() = (application as MainApp).analytics

    private lateinit var spinnerEventType: Spinner
    private lateinit var editTextEventValue: EditText
    private lateinit var buttonLog: Button
    private lateinit var buttonReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        setupEventTypeSpinner()
        setupButtons()
    }

    private fun findViews() {
        spinnerEventType = findViewById(R.id.spinner_event_name)
        editTextEventValue = findViewById(R.id.edittext_event_value)
        buttonLog = findViewById(R.id.button_log)
        buttonReset = findViewById(R.id.button_reset)
    }

    private fun setupEventTypeSpinner() {
        val eventTypes = TestEventType.entries.toTypedArray()
        spinnerEventType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, eventTypes)
    }

    private fun setupButtons() {
        buttonLog.setOnClickListener {
            val selectedEvent = spinnerEventType.selectedItem as TestEventType
            selectedEvent.log(editTextEventValue.text.toString(), analytics)
        }

        buttonReset.setOnClickListener {
            analytics.reset()
        }
    }
}

enum class TestEventType {
    SCREEN {
        override fun log(value: String, analytics: Analytics) {
            analytics.screen(title = value)
        }
    },
    TRACK {
        override fun log(value: String, analytics: Analytics) {
            analytics.track(name = value)
        }
    },
    IDENTIFY {
        override fun log(value: String, analytics: Analytics) {
            analytics.identify(userId = value)
        }
    };

    abstract fun log(value: String, analytics: Analytics)
}
