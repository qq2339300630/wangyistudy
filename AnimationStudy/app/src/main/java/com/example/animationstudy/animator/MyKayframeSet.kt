package com.example.animationstudy.animator

import android.animation.FloatEvaluator
import android.animation.TypeEvaluator

class MyKayframeSet(var keyframes: Array<MyFloatKeyframe?>) {
    var mEvaluator: TypeEvaluator<Number> = FloatEvaluator()
    var mFirstKeyframe: MyFloatKeyframe = keyframes[0]!!
    var mKeyframes: List<MyFloatKeyframe> = listOf(*keyframes) as List<MyFloatKeyframe>

    companion object {
        fun ofFloat(values: FloatArray): MyKayframeSet {
            val numKeyframes = values.size
            var keyframes = arrayOfNulls<MyFloatKeyframe>(numKeyframes)
            keyframes[0] = MyFloatKeyframe(0f, mValue = values[0])
            for (index in 1 until numKeyframes) {
                keyframes[index] =
                    MyFloatKeyframe((index * 1.0f) / (numKeyframes - 1), mValue = values[index])
            }
            return MyKayframeSet(keyframes)
        }
    }

    fun getValue(fraction:Float): Number? {
        var provKeyframe = mFirstKeyframe
        for (index in 1 until mKeyframes.size) {
            val nextKeyframe = mKeyframes[index]
            if (fraction < nextKeyframe.mFraction) {
                return mEvaluator.evaluate(fraction,provKeyframe.mValue,nextKeyframe.mValue)
            }
            provKeyframe = nextKeyframe
        }
        return provKeyframe.mValue
    }

}


