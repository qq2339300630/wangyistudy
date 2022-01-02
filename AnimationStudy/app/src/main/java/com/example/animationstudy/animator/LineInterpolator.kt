package com.example.animationstudy.animator

import android.animation.TimeInterpolator

class LineInterpolator:TimeInterpolator {
    override fun getInterpolation(p0: Float): Float {
        return p0
    }
}