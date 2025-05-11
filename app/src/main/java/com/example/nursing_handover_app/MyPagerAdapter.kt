//ViewPager2 어댑터
package com.example.nursing_handover_app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//FragmentStateAdapter 상속
class MyPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment() //메인화면
            1 -> SecondFragment() //근무자 조회
            2 -> ThirdFragment() //인수인계 작성
            3 -> ForthFragment() //인수인계 조회
            else -> FirstFragment()
        }
    }
}
