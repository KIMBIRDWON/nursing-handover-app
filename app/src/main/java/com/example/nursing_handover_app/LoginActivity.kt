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
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM users WHERE user_id = ? AND password = ?",
                arrayOf(Id, Pw)
            )

            if (cursor.moveToFirst()) { //로그인 성공
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                Toast.makeText(this, "${name}님 환영합니다", Toast.LENGTH_SHORT).show() //환영 메시지
                val intent = Intent(this, MainActivity::class.java) //메인 액티비티로 이동
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else { //로그인 실패
                Toast.makeText(this, "로그인 실패: 사용자 정보를 확인하세요", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
            db.close()
        }
    }
}