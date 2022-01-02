package com.example.animationstudy.animator

class VSYNCManager {
    val list = mutableListOf<AnimationFrameCallback>()
    val runnable = Runnable {
        while (true) {
            Thread.sleep(16)
            for (animationFrameCallback in list) {
                animationFrameCallback.doAnimationFrame(System.currentTimeMillis())
            }
        }
    }
    init {
        Thread(runnable).start()
    }

    companion object {
        private val ourInstance = VSYNCManager()
        fun getInstance(): VSYNCManager {
            return ourInstance
        }
    }


    interface AnimationFrameCallback {
        fun doAnimationFrame(currentTime:Long):Boolean
    }

    fun add(animationFrameCallback: AnimationFrameCallback){
        list.add(animationFrameCallback)
    }
}