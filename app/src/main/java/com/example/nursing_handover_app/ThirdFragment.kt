package com.example.nursing_handover_app

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//세 번째 탭입니다.
class ThirdFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_third, container, false)

        // 1. 날짜 선택
        val calendarView = rootView.findViewById<CalendarView>(R.id.visitPicker)
        val textViewDate = rootView.findViewById<TextView>(R.id.visitDate)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val formatted = "${year}년 ${month + 1}월 ${dayOfMonth}일"
            textViewDate.text = formatted
        }

        // 2. 내원경로 라디오버튼
        val radioButtons = listOf(
            rootView.findViewById<RadioButton>(R.id.er),
            rootView.findViewById<RadioButton>(R.id.outpatient),
            rootView.findViewById<RadioButton>(R.id.transfer),
            rootView.findViewById<RadioButton>(R.id.other)
        )
        val otherInput = rootView.findViewById<EditText>(R.id.otherInput)
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

        // 3. 과거력
        val historyInput = rootView.findViewById<EditText>(R.id.historyInput)
        val historyGroup = rootView.findViewById<RadioGroup>(R.id.historyGroup)
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

        // 4. 알레르기
        val allergyInput = rootView.findViewById<EditText>(R.id.allergyInput)
        val allergyGroup = rootView.findViewById<RadioGroup>(R.id.allergyGroup)
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

        // 5. 수술력
        val surgeryInput = rootView.findViewById<EditText>(R.id.surgeryInput)
        val surgeryGroup = rootView.findViewById<RadioGroup>(R.id.surgeryGroup)
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

        // 6. 배변
        val defacateInput = rootView.findViewById<EditText>(R.id.defacateInput)
        val defacateGroup = rootView.findViewById<RadioGroup>(R.id.defacateGroup)
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

        // 7. 검사결과 추가
        val testContainer = rootView.findViewById<LinearLayout>(R.id.testContainer)
        val addTestButton = rootView.findViewById<Button>(R.id.addTestButton)
        addTestButton.setOnClickListener {
            val testItem = inflater.inflate(R.layout.test_result_input, null)
            testContainer.addView(testItem)
        }

        // 8. 예정된 검사 추가
        val scheduledTestsContainer = rootView.findViewById<LinearLayout>(R.id.scheduledTestsContainer)
        val addScheduledTestButton = rootView.findViewById<Button>(R.id.addScheduledTestButton)
        addScheduledTestButton.setOnClickListener {
            val testView = inflater.inflate(R.layout.scheduled_test_item, null)
            val dateInput = testView.findViewById<EditText>(R.id.testDateInput)
            val fastingCheck = testView.findViewById<CheckBox>(R.id.fastingCheck)
            val fastingTimeInput = testView.findViewById<EditText>(R.id.fastingTimeInput)

            val today = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
            dateInput.setText(dateFormat.format(today.time))

            dateInput.setOnClickListener {
                val cal = Calendar.getInstance()
                val dpd = DatePickerDialog(
                    requireContext(),
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

        return rootView
    }
}