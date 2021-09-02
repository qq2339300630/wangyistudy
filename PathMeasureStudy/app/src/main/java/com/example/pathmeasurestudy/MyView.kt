package com.example.pathmeasurestudy

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 10f
        isAntiAlias = true
    }
    private val path = Path()
    private val pathMeasure = PathMeasure(path,false)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.addCircle(width / 2f, height / 2f, 200f, Path.Direction.CW)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path,paint)

//        pathMeasure.getPosTan()
//        pathMeasure.getSegment()
    }
}