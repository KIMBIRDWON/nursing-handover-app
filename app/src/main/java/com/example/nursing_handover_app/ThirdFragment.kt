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
import android.widget.Toast
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
            //rootView.findViewById<RadioButton>(R.id.other)
        )
        //val otherInput = rootView.findViewById<EditText>(R.id.otherInput)
        for (rb in radioButtons) {
            rb.setOnClickListener {
                radioButtons.forEach { it.isChecked = false }
                rb.isChecked = true
//                if (rb.id == R.id.other) {
//                    otherInput.isEnabled = true
//                    otherInput.requestFocus()
//                } else {
//                    otherInput.setText("")
//                    otherInput.isEnabled = false
//                }
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

        //DB 연동
        //DB helper 인스턴스 생성
        val dbHelper = AppDatabaseHelper(requireContext())
        val saveButton = rootView.findViewById<Button>(R.id.save)

        saveButton.setOnClickListener {
            //1. 각 입력값 읽기
            val hand_nurse = rootView.findViewById<EditText>(R.id.hand_nurse).text.toString()
            val take_nurse = rootView.findViewById<EditText>(R.id.take_nurse).text.toString()
            val write_date = rootView.findViewById<EditText>(R.id.write_date).text.toString()
            val room_num = rootView.findViewById<EditText>(R.id.room_num).text.toString().toIntOrNull() ?: 0
            val bed_num = when (rootView.findViewById<RadioGroup>(R.id.bed_num).checkedRadioButtonId) {
                R.id.bedOne -> "1번"
                R.id.bedTwo -> "2번"
                R.id.bedThree -> "3번"
                R.id.bedFour -> "4번"
                else -> ""
            }
            val name = rootView.findViewById<EditText>(R.id.name).text.toString()
            val sex = when (rootView.findViewById<RadioGroup>(R.id.sex).checkedRadioButtonId) {
                R.id.male -> "남"
                R.id.female -> "여"
                else -> ""
            }
            val age = rootView.findViewById<EditText>(R.id.age).text.toString().toIntOrNull() ?: 0
            val visitDate = rootView.findViewById<TextView>(R.id.visitDate).text.toString()
            val hospitalRouteGroup = when (rootView.findViewById<RadioGroup>(R.id.hospitalRouteGroup).checkedRadioButtonId) {
                R.id.er -> "응급실"
                R.id.outpatient -> "외래"
                R.id.transfer -> "타병원"
                else -> ""
            }
            //라디오그룹 선택값 추출
            val historyGroup = when (rootView.findViewById<RadioGroup>(R.id.historyGroup).checkedRadioButtonId) {
                R.id.historyYes -> "유"
                R.id.historyNo -> "무"
                else -> ""
            }
            //EditText 값 추출
            val historyInput = rootView.findViewById<EditText>(R.id.historyInput).text.toString()
            //라디오버튼 선택 시 EditText 활성/비활성 처리
            rootView.findViewById<RadioGroup>(R.id.historyGroup).setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.historyYes -> {
                        rootView.findViewById<EditText>(R.id.historyInput).isEnabled = true
                        rootView.findViewById<EditText>(R.id.historyInput).requestFocus()
                    }

                    R.id.historyNo -> {
                        rootView.findViewById<EditText>(R.id.historyInput).setText("")
                        rootView.findViewById<EditText>(R.id.historyInput).isEnabled = false
                    }
                }
            }
            val allergyGroup = when (rootView.findViewById<RadioGroup>(R.id.allergyGroup).checkedRadioButtonId) {
                R.id.allergyYes -> "유"
                R.id.allergyNo -> "무"
                else -> ""
            }
            val allergyInput = rootView.findViewById<EditText>(R.id.allergyInput).text.toString()
            val surgeryGroup = when (rootView.findViewById<RadioGroup>(R.id.surgeryGroup).checkedRadioButtonId) {
                R.id.surgeryYes -> "유"
                R.id.surgeryNo -> "무"
                else -> ""
            }
            val surgeryInput = rootView.findViewById<EditText>(R.id.surgeryInput).text.toString()
            val cause = rootView.findViewById<EditText>(R.id.cause).text.toString()
            val blood1 = rootView.findViewById<EditText>(R.id.blood1).text.toString().toIntOrNull() ?: 0
            val blood2 = rootView.findViewById<EditText>(R.id.blood2).text.toString().toIntOrNull() ?: 0
            val pulse = rootView.findViewById<EditText>(R.id.pulse).text.toString().toIntOrNull() ?: 0
            val breath = rootView.findViewById<EditText>(R.id.breath).text.toString().toIntOrNull() ?: 0
            val temperature = rootView.findViewById<EditText>(R.id.temperature).text.toString().toIntOrNull() ?: 0
            val oxy = rootView.findViewById<EditText>(R.id.oxy).text.toString().toIntOrNull() ?: 0
            val SpO2 = rootView.findViewById<EditText>(R.id.SpO2).text.toString().toIntOrNull() ?: 0
            val pee = rootView.findViewById<EditText>(R.id.pee).text.toString().toIntOrNull() ?: 0
            val defacateGroup = when (rootView.findViewById<RadioGroup>(R.id.defacateGroup).checkedRadioButtonId) {
                R.id.defacateYes -> "유"
                R.id.defacateNo -> "무"
                else -> ""
            }
            val defacateInput = rootView.findViewById<EditText>(R.id.defacateInput).text.toString()
            val pipe = rootView.findViewById<EditText>(R.id.pipe).text.toString().toIntOrNull() ?: 0
            val pipe_amount = rootView.findViewById<EditText>(R.id.pipe_amount).text.toString().toIntOrNull() ?: 0
            val color = rootView.findViewById<EditText>(R.id.color).text.toString()

            //2. DB에 저장
            dbHelper.insertHandover(
                hand_nurse,
                take_nurse,
                write_date,
                room_num,
                bed_num,
                name,
                sex,
                age,
                visitDate,
                hospitalRouteGroup,
                historyGroup,
                historyInput,
                allergyGroup,
                allergyInput,
                surgeryGroup,
                surgeryInput,
                cause,
                blood1,
                blood2,
                pulse,
                breath,
                temperature,
                oxy,
                SpO2,
                pee,
                defacateGroup,
                defacateInput,
                pipe,
                pipe_amount,
                color
            )

            Toast.makeText(requireContext(), "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }
}