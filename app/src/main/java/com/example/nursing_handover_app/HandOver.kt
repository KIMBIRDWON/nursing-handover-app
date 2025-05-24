package com.example.nursing_handover_app

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nursing_handover_app.databinding.ActivityHandOverBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        val allergyYes: RadioButton = findViewById(R.id.allergyYes)
        val allergyNo: RadioButton = findViewById(R.id.allergyNo)
        val allergyInput: EditText = findViewById(R.id.allergyInput)
        val allergyGroup: RadioGroup = findViewById(R.id.allergyGroup)

        allergyGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.allergyYes -> {
                    allergyInput.isEnabled = true
                    allergyInput.requestFocus()
                }
                R.id.allergyNo -> {
                    allergyInput.setText("")
                    allergyInput.isEnabled = false
                }
            }
        }

        val surgeryInput: EditText = findViewById(R.id.surgeryInput)
        val surgeryGroup: RadioGroup = findViewById(R.id.surgeryGroup)

        surgeryGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.surgeryYes -> {
                    surgeryInput.isEnabled = true
                    surgeryInput.requestFocus()
                }
                R.id.surgeryNo -> {
                    surgeryInput.setText("")
                    surgeryInput.isEnabled = false
                }
            }
        }

        val defacateInput: EditText = findViewById(R.id.defacateInput)
        val defacateGroup: RadioGroup = findViewById(R.id.defacateGroup)

        defacateGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.defacateYes -> {
                    defacateInput.isEnabled = true
                    defacateInput.requestFocus()
                }
                R.id.defacateNo -> {
                    defacateInput.setText("")
                    defacateInput.isEnabled = false
                }
            }
        }
        val testContainer = findViewById<LinearLayout>(R.id.testContainer)
        val addTestButton = findViewById<Button>(R.id.addTestButton)

        addTestButton.setOnClickListener {
            val testItem = layoutInflater.inflate(R.layout.test_result_input, null)
            testContainer.addView(testItem)
        }

        val scheduledTestsContainer = findViewById<LinearLayout>(R.id.scheduledTestsContainer)
        val addScheduledTestButton = findViewById<Button>(R.id.addScheduledTestButton)

        addScheduledTestButton.setOnClickListener {
            val testView = layoutInflater.inflate(R.layout.scheduled_test_item, null)

            val dateInput = testView.findViewById<EditText>(R.id.testDateInput)
            val fastingCheck = testView.findViewById<CheckBox>(R.id.fastingCheck)
            val fastingTimeInput = testView.findViewById<EditText>(R.id.fastingTimeInput)

            val today = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
            dateInput.setText(dateFormat.format(today.time))

            dateInput.setOnClickListener {
                val cal = Calendar.getInstance()
                val dpd = DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(year, month, dayOfMonth)
                        dateInput.setText(dateFormat.format(selectedDate.time))
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
                dpd.show()
            }

            fastingCheck.setOnCheckedChangeListener { _, isChecked ->
                fastingTimeInput.isEnabled = isChecked
                if (!isChecked) fastingTimeInput.setText("")
            }

            scheduledTestsContainer.addView(testView)
        }

    }
}