package com.example.pathmeasurestudy

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI
import kotlin.math.atan2

class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 10f
        isAntiAlias = true
    }
    private val floatArrayPoint = floatArrayOf(0f,0f)
    private val floatArrayTan = floatArrayOf(0f,0f)
    private val option = BitmapFactory.Options().apply {
        inSampleSize = 4
    }
    private val bitmap = BitmapFactory.decodeResource(resources,R.mipmap.arrow,option)
    private val path = Path()
    private val pathMeasure = PathMeasure(path,false)
    private var mProcess = 0f
    private val mMatrix = Matrix()

    private val animate = ValueAnimator.ofFloat(0f,1f).apply {
        duration = 2000
        repeatCount = 10
        addUpdateListener {
            mProcess = it.animatedValue as Float
            invalidate()
        }
    }

    // todo  初始化pathMeasure与path
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.addCircle(width / 2f, height / 2f, 200f, Path.Direction.CW)
        pathMeasure.setPath(path,false)
        animate.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path,paint)
        pathMeasure.getPosTan(pathMeasure.length*mProcess,floatArrayPoint,floatArrayTan)
        val angle = atan2(floatArrayTan[0].toDouble(),floatArrayTan[1].toDouble())*180f/ PI
        mMatrix.reset()
        mMatrix.postRotate(- angle.toFloat() + 90,bitmap.width/2f,bitmap.height/2f)
        mMatrix.postTranslate(floatArrayPoint[0] - bitmap.width/2f,floatArrayPoint[1] -bitmap.height/2f)
        canvas.drawBitmap(bitmap,mMatrix,paint)
    }
}