package com.example.nursing_handover_app

import android.os.Bundle
import android.widget.CalendarView
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nursing_handover_app.databinding.ActivityHandOverBinding

class HandOver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityHandOverBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // MainActivity.kt
        val calendarView = findViewById<CalendarView>(R.id.visitPicker)
        val textViewDate = findViewById<TextView>(R.id.visitDate)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val formatted = "${year}년 ${month + 1}월 ${dayOfMonth}일"
            textViewDate.text = formatted
        }
        val radioButtons = listOf(
            findViewById<RadioButton>(R.id.er),
            findViewById<RadioButton>(R.id.outpatient),
            findViewById<RadioButton>(R.id.transfer),
            findViewById<RadioButton>(R.id.other)
        )
        val otherInput = findViewById<EditText>(R.id.otherInput)

        for (rb in radioButtons) {
            rb.setOnClickListener {
                radioButtons.forEach { it.isChecked = false }
                rb.isChecked = true
                if (rb.id == R.id.other) {
                    otherInput.isEnabled = true
                    otherInput.requestFocus()
                } else {
                    otherInput.setText("")
                    otherInput.isEnabled = false
                }
            }
        }

        val historyYes: RadioButton = findViewById(R.id.historyYes)
        val historyNo: RadioButton = findViewById(R.id.historyNo)
        val historyInput: EditText = findViewById(R.id.historyInput)
        val historyGroup: RadioGroup = findViewById(R.id.historyGroup)

        historyGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.historyYes -> {
                    historyInput.isEnabled = true
                    historyInput.requestFocus()
                }
                R.id.historyNo -> {
                    historyInput.setText("")
                    historyInput.isEnabled = false
                }
            }
        }
    }
}