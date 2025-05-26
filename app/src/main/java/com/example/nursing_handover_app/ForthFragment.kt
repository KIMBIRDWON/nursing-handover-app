package com.example.nursing_handover_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForthFragment : Fragment() {
    private lateinit var dbHelper: AppDatabaseHelper

    data class HandoverItem(
        val id: Int,
        val write_date: String,
        val hand_nurse: String,
        val take_nurse: String,
        val name: String
    )

    //상세 데이터 클래스
    data class HandoverDetail(
        val id: Int,
        val write_date: String,
        val hand_nurse: String,
        val take_nurse: String,
        val name: String,
        val room_num: Int,
        val bed_num: String,
        val sex: String,
        val age: Int,
        val visitDate: String,
        val hospitalRouteGroup: String,
        val historyGroup: String,
        val historyInput: String,
        val allergyGroup: String,
        val allergyInput: String,
        val surgeryGroup: String,
        val surgeryInput: String,
        val cause: String,
        val blood1: Int,
        val blood2: Int,
        val pulse: Int,
        val breath: Int,
        val temperature: Int,
        val oxy: Int,
        val SpO2: Int,
        val pee: Int,
        val defacateGroup: String,
        val defacateInput: String,
        val pipe: Int,
        val pipe_amount: Int,
        val color: String
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = AppDatabaseHelper(requireContext())
        val listView = view.findViewById<ListView>(R.id.listView)

        lifecycleScope.launch(Dispatchers.IO) {
            val handoverList = loadHandoversFromDb()
            withContext(Dispatchers.Main) {
                val adapter = object : BaseAdapter() {
                    override fun getCount() = handoverList.size
                    override fun getItem(position: Int) = handoverList[position]
                    override fun getItemId(position: Int) = position.toLong()
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val v = convertView ?: LayoutInflater.from(parent.context)
                            .inflate(R.layout.handover_list, parent, false)
                        val item = handoverList[position]

                        v.findViewById<TextView>(R.id.write_date).text = item.write_date
                        v.findViewById<TextView>(R.id.hand_nurse).text = item.hand_nurse
                        v.findViewById<TextView>(R.id.take_nurse).text = item.take_nurse
                        v.findViewById<TextView>(R.id.name).text = item.name

                        return v
                    }
                }
                listView.adapter = adapter

                //리스트 아이템 클릭 리스너
                listView.setOnItemClickListener { _, _, position, _ ->
                    val item = handoverList[position]
                    showHandoverDetailDialog(item.id)}
            }
        }
    }

    private suspend fun loadHandoversFromDb(): List<HandoverItem> {
        return withContext(Dispatchers.IO) {
            val handoverList = mutableListOf<HandoverItem>()
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery("""
                SELECT id, write_date, hand_nurse, take_nurse, name
                FROM handover
                ORDER BY id DESC
            """.trimIndent(), null)
            try {
                while (cursor.moveToNext()) {
                    handoverList.add(
                        HandoverItem(
                            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            write_date = cursor.getString(cursor.getColumnIndexOrThrow("write_date")),
                            hand_nurse = cursor.getString(cursor.getColumnIndexOrThrow("hand_nurse")),
                            take_nurse = cursor.getString(cursor.getColumnIndexOrThrow("take_nurse")),
                            name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                        )
                    )
                }
            } finally {
                cursor.close()
                db.close()
            }
            handoverList
        }
    }
    //상세 조회 함수
    private fun getHandoverById(id: Int): HandoverDetail? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM handover WHERE id = ?", arrayOf(id.toString()))
        return try {
            if (cursor.moveToFirst()) {
                HandoverDetail(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    write_date = cursor.getString(cursor.getColumnIndexOrThrow("write_date")),
                    hand_nurse = cursor.getString(cursor.getColumnIndexOrThrow("hand_nurse")),
                    take_nurse = cursor.getString(cursor.getColumnIndexOrThrow("take_nurse")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    room_num = cursor.getInt(cursor.getColumnIndexOrThrow("room_num")),
                    bed_num = cursor.getString(cursor.getColumnIndexOrThrow("bed_num")),
                    sex = cursor.getString(cursor.getColumnIndexOrThrow("sex")),
                    age = cursor.getInt(cursor.getColumnIndexOrThrow("age")),
                    visitDate = cursor.getString(cursor.getColumnIndexOrThrow("visitDate")),
                    hospitalRouteGroup = cursor.getString(cursor.getColumnIndexOrThrow("hospitalRouteGroup")),
                    historyGroup = cursor.getString(cursor.getColumnIndexOrThrow("historyGroup")),
                    historyInput = cursor.getString(cursor.getColumnIndexOrThrow("historyInput")),
                    allergyGroup = cursor.getString(cursor.getColumnIndexOrThrow("allergyGroup")),
                    allergyInput = cursor.getString(cursor.getColumnIndexOrThrow("allergyInput")),
                    surgeryGroup = cursor.getString(cursor.getColumnIndexOrThrow("surgeryGroup")),
                    surgeryInput = cursor.getString(cursor.getColumnIndexOrThrow("surgeryInput")),
                    cause = cursor.getString(cursor.getColumnIndexOrThrow("cause")),
                    blood1 = cursor.getInt(cursor.getColumnIndexOrThrow("blood1")),
                    blood2 = cursor.getInt(cursor.getColumnIndexOrThrow("blood2")),
                    pulse = cursor.getInt(cursor.getColumnIndexOrThrow("pulse")),
                    breath = cursor.getInt(cursor.getColumnIndexOrThrow("breath")),
                    temperature = cursor.getInt(cursor.getColumnIndexOrThrow("temperature")),
                    oxy = cursor.getInt(cursor.getColumnIndexOrThrow("oxy")),
                    SpO2 = cursor.getInt(cursor.getColumnIndexOrThrow("SpO2")),
                    pee = cursor.getInt(cursor.getColumnIndexOrThrow("pee")),
                    defacateGroup = cursor.getString(cursor.getColumnIndexOrThrow("defacateGroup")),
                    defacateInput = cursor.getString(cursor.getColumnIndexOrThrow("defacateInput")),
                    pipe = cursor.getInt(cursor.getColumnIndexOrThrow("pipe")),
                    pipe_amount = cursor.getInt(cursor.getColumnIndexOrThrow("pipe_amount")),
                    color = cursor.getString(cursor.getColumnIndexOrThrow("color"))
                )
            } else null
        } finally {
            cursor.close()
            db.close()
        }
    }
    //상세 다이얼로그 함수
    private fun showHandoverDetailDialog(id: Int) {
        val detail = getHandoverById(id) ?: return
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_handover_detail, null)

        //1. 텍스트 필드 바인딩
        dialogView.findViewById<TextView>(R.id.visitDate).text = detail.visitDate
        //EditText는 setText() 사용
        dialogView.findViewById<EditText>(R.id.hand_nurse).setText(detail.hand_nurse)
        dialogView.findViewById<EditText>(R.id.take_nurse).setText(detail.take_nurse)
        dialogView.findViewById<EditText>(R.id.write_date).setText(detail.write_date)
        dialogView.findViewById<EditText>(R.id.room_num).setText(detail.room_num.toString())
        dialogView.findViewById<EditText>(R.id.name).setText(detail.name)
        dialogView.findViewById<EditText>(R.id.age).setText(detail.age.toString())
        dialogView.findViewById<EditText>(R.id.historyInput).setText(detail.historyInput)
        dialogView.findViewById<EditText>(R.id.allergyInput).setText(detail.allergyInput)
        dialogView.findViewById<EditText>(R.id.surgeryInput).setText(detail.surgeryInput)
        dialogView.findViewById<EditText>(R.id.cause).setText(detail.cause)
        dialogView.findViewById<EditText>(R.id.blood1).setText(detail.blood1.toString())
        dialogView.findViewById<EditText>(R.id.blood2).setText(detail.blood2.toString())
        dialogView.findViewById<EditText>(R.id.pulse).setText(detail.pulse.toString())
        dialogView.findViewById<EditText>(R.id.breath).setText(detail.breath.toString())
        dialogView.findViewById<EditText>(R.id.temperature).setText(detail.temperature.toString())
        dialogView.findViewById<EditText>(R.id.oxy).setText(detail.oxy.toString())
        dialogView.findViewById<EditText>(R.id.SpO2).setText(detail.SpO2.toString())
        dialogView.findViewById<EditText>(R.id.pee).setText(detail.pee.toString())
        dialogView.findViewById<EditText>(R.id.defacateInput).setText(detail.defacateInput)
        dialogView.findViewById<EditText>(R.id.pipe).setText(detail.pipe.toString())
        dialogView.findViewById<EditText>(R.id.pipe_amount).setText(detail.pipe_amount.toString())
        dialogView.findViewById<EditText>(R.id.color).setText(detail.color)

        //2. 라디오버튼 처리
        //성별
        val sexGroup = dialogView.findViewById<RadioGroup>(R.id.sex)
        when (detail.sex) {
            "남" -> sexGroup.check(R.id.male)
            "여" -> sexGroup.check(R.id.female)
        }

        //침상 번호
        val bedNumGroup = dialogView.findViewById<RadioGroup>(R.id.bed_num)
        when (detail.bed_num) {
            "1번" -> bedNumGroup.check(R.id.bedOne)
            "2번" -> bedNumGroup.check(R.id.bedTwo)
            "3번" -> bedNumGroup.check(R.id.bedThree)
            "4번" -> bedNumGroup.check(R.id.bedFour)
        }

        //입원 경로
        val hospitalRouteGroup = dialogView.findViewById<RadioGroup>(R.id.hospitalRouteGroup)
        when (detail.hospitalRouteGroup) {
            "응급실" -> hospitalRouteGroup.check(R.id.er)
            "외래" -> hospitalRouteGroup.check(R.id.outpatient)
            "타병원" -> hospitalRouteGroup.check(R.id.transfer)
        }

        //과거력
        val historyGroup = dialogView.findViewById<RadioGroup>(R.id.historyGroup)
        when (detail.historyGroup) {
            "유" -> historyGroup.check(R.id.historyYes)
            "무" -> historyGroup.check(R.id.historyNo)
        }

        //알레르기
        val allergyGroup = dialogView.findViewById<RadioGroup>(R.id.allergyGroup)
        when (detail.allergyGroup) {
            "유" -> allergyGroup.check(R.id.allergyYes)
            "무" -> allergyGroup.check(R.id.allergyNo)
        }

        //수술력
        val surgeryGroup = dialogView.findViewById<RadioGroup>(R.id.surgeryGroup)
        when (detail.surgeryGroup) {
            "유" -> surgeryGroup.check(R.id.surgeryYes)
            "무" -> surgeryGroup.check(R.id.surgeryNo)
        }

        //배변
        val defecateGroup = dialogView.findViewById<RadioGroup>(R.id.defacateGroup)
        when (detail.defacateGroup) {
            "유" -> defecateGroup.check(R.id.defacateYes)
            "무" -> defecateGroup.check(R.id.defacateNo)
        }

//        //3. 다이얼로그 표시
//        android.app.AlertDialog.Builder(requireContext())
//            .setView(dialogView)
//            .setPositiveButton("닫기") { dialog, _ -> dialog.dismiss() }
//            .show()

        //3. 다이얼로그 표시
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnClose).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        //읽기 전용으로 전환(더 추가해야 함)
//        val editIds = listOf(
//            R.id.historyInput, R.id.allergyInput, R.id.surgeryInput, R.id.defacateInput,
//        )
//        editIds.forEach { id ->
//            dialogView.findViewById<EditText>(id)?.apply {
//                isEnabled = false
//                isFocusable = false
//                isClickable = false
//            }
//        }
        val radioIds = listOf(
            R.id.historyYes, R.id.historyNo,
            R.id.allergyYes, R.id.allergyNo,
            R.id.surgeryYes, R.id.surgeryNo,
            R.id.defacateYes, R.id.defacateNo,
            R.id.er, R.id.outpatient, R.id.transfer
        )
        radioIds.forEach { id ->
            dialogView.findViewById<RadioButton>(id)?.isEnabled = false
        }
    }
}