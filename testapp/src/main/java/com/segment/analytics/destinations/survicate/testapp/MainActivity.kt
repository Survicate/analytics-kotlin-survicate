package com.segment.analytics.destinations.survicate.testapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.segment.analytics.kotlin.core.Analytics

class MainActivity : AppCompatActivity() {

    private val analytics: Analytics get() = (application as MainApp).analytics

    private lateinit var spinnerEventType: Spinner
    private lateinit var editTextEventValue: EditText
    private lateinit var buttonLog: Button
    private lateinit var buttonReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        applyWindowInsets()
        setupEventTypeSpinner()
        setupButtons()
    }

    private fun findViews() {
        spinnerEventType = findViewById(R.id.spinner_event_name)
        editTextEventValue = findViewById(R.id.edittext_event_value)
        buttonLog = findViewById(R.id.button_log)
        buttonReset = findViewById(R.id.button_reset)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.content_layout)) { view, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                        WindowInsetsCompat.Type.displayCutout() or
                        WindowInsetsCompat.Type.ime()
            )
            view.updatePadding(
                left = insets.left,
                top = insets.top,
                right = insets.right,
                bottom = insets.bottom,
            )
            windowInsets
        }
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
