package com.example.pageviewstudy

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val container:ParallaxContainer = findViewById(R.id.parallax_container)
        container.setUp(R.layout.view_intro_1,
            R.layout.view_intro_2,
            R.layout.view_intro_3,
            R.layout.view_intro_4,
            R.layout.view_intro_5,
            R.layout.view_intro_6,
            R.layout.view_intro_7,
            R.layout.view_login)
        val iv_man:ImageView = findViewById(R.id.iv_man)
        iv_man.setBackgroundResource(R.drawable.man_run)
        container.iv_main = iv_man
    }
}