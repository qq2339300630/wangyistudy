package com.example.pingadaptstudy.ui

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
       lateinit var mContext:App
    }


}