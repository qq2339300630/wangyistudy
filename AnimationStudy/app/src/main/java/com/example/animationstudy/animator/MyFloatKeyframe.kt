package com.example.animationstudy.animator

import kotlin.reflect.KClass

data class MyFloatKeyframe(var mFraction: Float, var mValueType: KClass<Float> = Float::class, var mValue:Float)