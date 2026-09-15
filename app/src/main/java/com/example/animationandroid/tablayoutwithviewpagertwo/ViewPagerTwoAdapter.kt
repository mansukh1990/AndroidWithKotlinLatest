package com.example.animationandroid.tablayoutwithviewpagertwo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animationandroid.tablayoutwithviewpager.CallsFragment
import com.example.animationandroid.tablayoutwithviewpager.ChatFragment
import com.example.animationandroid.tablayoutwithviewpager.StatusFragment

class ViewPagerTwoAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                ChatFragment()
            }

            1 -> {
                StatusFragment()
            }

            2 -> {
                CallsFragment()
            }

            else -> {
                CallsFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}