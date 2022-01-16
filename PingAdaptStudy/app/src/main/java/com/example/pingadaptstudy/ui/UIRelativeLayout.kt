package com.example.pingadaptstudy.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.view.children

class UIRelativeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private var flag = true


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (flag) {
            flag = false
            val scaleX = UIUtils.getHorizontalScaleValue()
            val scaleY = UIUtils.getVerticalScaleValue()
            for (child in children) {
                val layoutParams = child.layoutParams
                layoutParams.width = (layoutParams.width * scaleX).toInt()
                layoutParams.height = (layoutParams.height * scaleY).toInt()
            }
        }
    }

}