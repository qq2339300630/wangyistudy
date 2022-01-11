package com.example.pageviewstudy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ParallaxPagerAdapter(
    fragmentManager: FragmentManager,
    val fragments: MutableList<ParallaxFragment>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}