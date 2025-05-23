package com.example.nursing_handover_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//두 번째 탭입니다.
class SecondFragment : Fragment() {
    private lateinit var dbHelper: AppDatabaseHelper

    data class User(
        val id: Int,
        val name: String,
        val dept: String,
        val position: String,
        val image: String?,
        val phone: String?
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    private fun showScheduleDialog(schedule: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_schedule, null)
        dialogView.findViewById<TextView>(R.id.tvSchedule).text = schedule

        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnClose).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = AppDatabaseHelper(requireContext())
        val listView = view.findViewById<ListView>(R.id.listView)

        lifecycleScope.launch(Dispatchers.IO) {
            val userList = loadUsersFromDb()
            withContext(Dispatchers.Main) {
                val adapter = object : BaseAdapter() {
                    override fun getCount() = userList.size
                    override fun getItem(position: Int) = userList[position]
                    override fun getItemId(position: Int) = position.toLong()
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val v = convertView ?: LayoutInflater.from(parent.context)
                            .inflate(R.layout.user_list, parent, false)
                        val user = userList[position]
                        //텍스트 설정
                        v.findViewById<TextView>(R.id.name).text = user.name
                        v.findViewById<TextView>(R.id.dept).text = user.dept
                        v.findViewById<TextView>(R.id.position).text = user.position
                        v.findViewById<TextView>(R.id.phone).text = user.phone ?: ""
                        //이미지 설정
                        val imageView = v.findViewById<ImageView>(R.id.userImage)
                        imageView.setImageResource(R.drawable.profile) //default : 기본 이미지

                        if (user.image != null) {
                            val resId = parent.context.resources.getIdentifier(
                                user.image, "drawable", parent.context.packageName
                            )
                            if (resId != 0) {
                                imageView.setImageResource(resId)
                            }
                        }
                        return v
                    }
                }
                listView.adapter = adapter

                listView.setOnItemClickListener { _, _, position, _ ->
                    val user = userList[position]
                    val schedule = dbHelper.getScheduleByUserId(user.id)
                    showScheduleDialog(schedule)
                }
            }
        }
    }
    private suspend fun loadUsersFromDb(): List<User> {
        return withContext(Dispatchers.IO) {
            val userList = mutableListOf<User>()
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery("SELECT id, name, dept, position, image, phone FROM users", null)
            try {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val dept = cursor.getString(cursor.getColumnIndexOrThrow("dept"))
                    val position = cursor.getString(cursor.getColumnIndexOrThrow("position"))
                    val image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                    val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                    userList.add(User(id, name, dept, position, image, phone))
                }
            } finally {
                cursor.close()
            }
            userList
        }
    }
    //DB에서 데이터 읽기
//        val userList = mutableListOf<User>()
//        val db = dbHelper.readableDatabase
//        val cursor = db.rawQuery("SELECT id, name, dept, position, image, phone FROM users", null)
//        while (cursor.moveToNext()) {
//            val id = cursor.getInt(0)
//            val name = cursor.getString(1)
//            val dept = cursor.getString(2)
//            val position = cursor.getString(3)
//            val image = cursor.getString(4)
//            //val phone = cursor.getString(5)
//            val phoneIndex = cursor.getColumnIndex("phone")
//            val phone = if (phoneIndex != -1) cursor.getString(phoneIndex) else null
//            userList.add(User(id, name, dept, position, image, phone))
//        }
//        cursor.close()

        //커스텀 어댑터
//        val adapter = object : BaseAdapter() {
//            override fun getCount() = userList.size
//            override fun getItem(position: Int) = userList[position]
//            override fun getItemId(position: Int) = position.toLong()
//            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//                val v = convertView ?: LayoutInflater.from(parent.context)
//                    .inflate(R.layout.user_list, parent, false)
//                val user = userList[position]
//                //텍스트 설정
//                v.findViewById<TextView>(R.id.name).text = user.name
//                v.findViewById<TextView>(R.id.dept).text = user.dept
//                v.findViewById<TextView>(R.id.position).text = user.position
//                v.findViewById<TextView>(R.id.phone).text = user.phone ?: ""
//                //이미지 설정
//                val imageView = v.findViewById<ImageView>(R.id.userImage)
//                imageView.setImageResource(R.drawable.profile) //default : 기본 이미지
//
//                if (user.image != null) {
//                    val resId = parent.context.resources.getIdentifier(
//                        user.image, "drawable", parent.context.packageName
//                    )
//                    if (resId != 0) {
//                        imageView.setImageResource(resId)
//                    }
//                }
//                return v
//            }
//        }
//        listView.adapter = adapter

        //리스트 클릭 시 id로 스케줄 조회
//        listView.setOnItemClickListener { _, _, position, _ ->
//            val user = userList[position]
//            val schedule = dbHelper.getScheduleByUserId(user.id)
//            showScheduleDialog(schedule)
//        }
//    }
}