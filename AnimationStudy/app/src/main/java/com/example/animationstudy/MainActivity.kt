package com.example.animationstudy

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.animationstudy.animator.MyObjectAnimator
import com.example.animationstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        viewBinding.tvMsg.setOnClickListener {
//            val objectAnimator = MyObjectAnimator.ofFloat(it, "scaleX", 1f,2f)
//            objectAnimator.mDuration = 3000
//            objectAnimator.start()

            val objectAnimator2 = ObjectAnimator.ofFloat(it, "scaleX", 2f)
            objectAnimator2.duration = 3000
            objectAnimator2.start()
        }
    }
}