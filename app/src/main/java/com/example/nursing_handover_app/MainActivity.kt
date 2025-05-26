package com.example.nursing_handover_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nursing_handover_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //toolbar와 DrawerLayout 연동(햄버거 아이콘 설정)
        toggle = ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.drawer_open, R.string.drawer_close
        ).apply {
            binding.drawer.addDrawerListener(this)
            syncState()
        }

        //로그인 정보 연동
        val headerView = binding.mainDrawer.getHeaderView(0)
        val userName = intent.getStringExtra("USER_NAME") ?: "이름"
        val userDept = intent.getStringExtra("USER_DEPT") ?: "전공"
        val userImage = intent.getIntExtra("USER_IMAGE", R.drawable.profile)

        headerView.findViewById<TextView>(R.id.name).text = userName
        headerView.findViewById<TextView>(R.id.dept).text = userDept
        headerView.findViewById<ImageView>(R.id.image).setImageResource(userImage)

        //로그아웃
        binding.mainDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }

        val tabIcons = arrayOf( //Tab 아이콘
            R.drawable.home,
            R.drawable.people,
            R.drawable.note,
            R.drawable.list
        )
        for (i in tabIcons.indices) {
            val tab = binding.tab.newTab()
            tab.setIcon(tabIcons[i])
            binding.tab.addTab(tab)
        }

        binding.tab.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> FirstFragment()
                    1 -> SecondFragment()
                    2 -> ThirdFragment()
                    3 -> ForthFragment()
                    else -> FirstFragment()
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
        })

        //프래그먼트 트랜잭션
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FirstFragment())
                .commit()
        }
    }

    fun selectTab(position: Int) {
        binding.tab.getTabAt(position)?.select()
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean { //툴바 로그아웃
//        return when (item.itemId) {
//            R.id.logout -> {
//                logout()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean { //사이드 메뉴 로그아웃
//        menuInflater.inflate(R.menu.menu_navigation, menu)
//        binding.mainDrawer.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.logout -> {
//                    logout()
//                    true
//                }
//                else -> false
//            }
//        }
//        return true
//    }

    private fun logout() { //로그아웃
        val prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        prefs.edit().clear().apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}