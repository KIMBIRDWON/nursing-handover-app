package com.example.nursing_handover_app

import android.os.Bundle
import android.view.MenuItem
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

        //toolbar와 DrawerLayout 연동(햄버거 아이콘 설정)
        toggle = ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.drawer_open, R.string.drawer_close
        ).apply {
            binding.drawer.addDrawerListener(this)
            syncState()
        }

        //binding.viewpager.adapter = MyPagerAdapter(this)

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

        //Tab과 ViewPager2 연결
//        TabLayoutMediator(binding.tab, binding.viewpager) { tab, position ->
//            tab.setIcon(tabIcons[position])
//            /*
//            tab.text = when (position) {
//                0 -> "홈"
//                1 -> "근무자"
//                2 -> "작성"
//                3 -> "조회"
//                else -> ""
//            }*/
//        }.attach()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}