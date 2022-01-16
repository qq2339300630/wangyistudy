package com.example.pingadaptstudy.ui

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object UIUtils {
    val STANDARD_WIDTH = 600f
    val STANDARD_HEIGHT = 1000f
    var displayMetricsWidth = 0f
    var displayMetricsHeight = 0f
    var mSystemBarHeight = 0


    init {
        val windowManager = App.mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        if (displayMetricsWidth == 0f || displayMetricsHeight == 0f) {
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            mSystemBarHeight = getSystemBarHeight()
            if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                displayMetricsWidth = displayMetrics.heightPixels.toFloat()
                displayMetricsHeight = (displayMetrics.widthPixels - mSystemBarHeight).toFloat()
            } else {
                displayMetricsWidth = displayMetrics.widthPixels.toFloat()
                displayMetricsHeight = (displayMetrics.heightPixels - mSystemBarHeight).toFloat()
            }
        }
    }

    fun getHorizontalScaleValue(): Float {
        return displayMetricsWidth / STANDARD_WIDTH
    }

    fun getVerticalScaleValue(): Float {
        return displayMetricsHeight / STANDARD_HEIGHT
    }

    private fun getSystemBarHeight(): Int {
        var height = 0;
        var resourceId =
            App.mContext.resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = App.mContext.resources.getDimensionPixelSize(resourceId);
        }
        return height
    }
}