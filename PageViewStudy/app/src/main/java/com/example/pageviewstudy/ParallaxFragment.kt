package com.example.pageviewstudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ParallaxFragment: Fragment() {
    val parallaxViews = mutableListOf<View>()

    override fun onCreateView(
        original: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments;
        val layoutId = args?.getInt("layoutId")
        val inflater = ParallaxLayoutInflater(original,requireActivity(),this)
        return inflater.inflate(layoutId!!,null)
    }
}