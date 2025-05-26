package com.example.nursing_handover_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nursing_handover_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: AppDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = AppDatabaseHelper(this)

        binding.loginBtn.setOnClickListener { //로그인
            val Id = binding.id.text.toString()
            val Pw = binding.password.text.toString()

            if (Id.isBlank() || Pw.isBlank()) { //공백 존재시
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM users WHERE user_id = ? AND password = ?",
                arrayOf(Id, Pw)
            )

            if (cursor.moveToFirst()) { //로그인 성공
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val dept = cursor.getString(cursor.getColumnIndexOrThrow("dept"))
                val imageName = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                val imageResId = resources.getIdentifier(imageName, "drawable", packageName)

                Toast.makeText(this, "${name}님 환영합니다.", Toast.LENGTH_SHORT).show() // 환영 메시지

                //사용자 정보를 Intent로 전달
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("USER_NAME", name)
                intent.putExtra("USER_DEPT", dept)
                intent.putExtra("USER_IMAGE", imageResId)
                startActivity(intent)
                finish()
            } else { //로그인 실패
                Toast.makeText(this, "아이디 또는 비밀번호가 잘못 되었습니다.", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
            db.close()
        }
    }
}