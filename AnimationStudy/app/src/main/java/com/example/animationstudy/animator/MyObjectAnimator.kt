package com.example.animationstudy.animator

import android.view.View
import java.lang.ref.WeakReference

class MyObjectAnimator(view: View,propertyName:String,vararg value:Float):VSYNCManager.AnimationFrameCallback {

    val target = WeakReference<View>(view)
    val myFloatPropertyValuesHolder = MyFloatPropertyValuesHolder(propertyName,*value)
    var mStartTime:Long = -1
    var mDuration = 0
    var index = 0
    var interpolator: TimeInterpolator? = null

    companion object {
        val TAG = "tuch"
        fun ofFloat(view: View, propertyName: String, vararg values: Float): MyObjectAnimator {
            return MyObjectAnimator(view, propertyName, *values)
        }
    }

    override fun doAnimationFrame(currentTime: Long): Boolean {
          val total = mDuration / 16
          var fraction = ((index++ * 1.0f) / total).toFloat()
         if (interpolator != null) {
             fraction = interpolator!!.getInterpolator(fraction)
         }
         if (index > total) {
             index = 0
         }
        target.get()?.let { myFloatPropertyValuesHolder.setAnimatedValue(it,fraction) }
        return false
    }

    fun start() {
        myFloatPropertyValuesHolder.setupSetter(target)
        mStartTime = System.currentTimeMillis()
        VSYNCManager.getInstance().add(this)
    }
}