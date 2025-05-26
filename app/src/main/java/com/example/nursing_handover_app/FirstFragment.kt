package com.example.nursing_handover_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

//첫 번째 탭입니다.
class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //인수하기(takeover) 버튼 클릭 시
        val btnTakeover = view.findViewById<Button>(R.id.takeover)
        btnTakeover.setOnClickListener {
            //ThirdFragment로 이동
            (activity as? MainActivity)?.selectTab(3)
        }

        //조회하기(check) 버튼 클릭 시
        val btnCheck = view.findViewById<Button>(R.id.check)
        btnCheck.setOnClickListener {
            //SecondFragment로 이동
            (activity as? MainActivity)?.selectTab(1)
        }
    }
}