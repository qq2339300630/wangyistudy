package com.example.animationstudy.animator

import android.util.Log
import android.view.View
import java.lang.ref.WeakReference
import java.lang.reflect.Method

class MyFloatPropertyValuesHolder(propertyName: String, vararg value: Float) {
    val mPropertyName = propertyName
    val mValueType = Float::class.java
    lateinit var mSetter:Method
    var mKeyFrames:MyKayframeSet = MyKayframeSet.ofFloat(value)

    fun setupSetter(target: WeakReference<View>) {
        val firstLetter = Character.toUpperCase(mPropertyName[0])
        val theRest = mPropertyName.substring(1)
        val methodName = "set$firstLetter$theRest"
        mSetter = View::class.java.getMethod(methodName,Float::class.java)
    }

    fun setAnimatedValue(target: View,fraction:Float) {
        val value = mKeyFrames.getValue(fraction) as Float
        mSetter.invoke(target,value)
    }

}