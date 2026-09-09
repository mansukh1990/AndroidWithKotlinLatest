package com.example.animationandroid.tablayoutwithviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                ChatFragment()
            }

            1 -> {
                StatusFragment()
            }

            else -> {
                CallsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3 //no. of tabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Chats"
            1 -> "Status"
            else -> "Calls"
        }
    }
}