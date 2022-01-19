package com.example.pingadaptstudy.ui

import android.view.View
import android.widget.RelativeLayout


object ViewCalculateUtil {
    fun setViewLayoutParam(view: View,width:Int,height:Int,leftMargin:Int,topMargin:Int,rightMargin:Int,bottomMargin:Int) {
        val scaleX = UIUtils.getHorizontalScaleValue()
        val scaleY = UIUtils.getVerticalScaleValue()
        var layoutParms = view.layoutParams as RelativeLayout.LayoutParams
        layoutParms.width = (layoutParms.width * scaleX).toInt()
        layoutParms.height = (layoutParms.height * scaleY).toInt()
        layoutParms.leftMargin = (layoutParms.leftMargin * scaleX).toInt()
        layoutParms.rightMargin = (layoutParms.rightMargin * scaleX).toInt()
        layoutParms.topMargin = (layoutParms.topMargin * scaleX).toInt()
        layoutParms.bottomMargin = (layoutParms.bottomMargin * scaleX).toInt()

    }
}