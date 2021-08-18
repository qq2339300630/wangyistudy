package com.cjj.imagestudy

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    private val list = mutableListOf<Ball>()
    private val d = 2
    private var isClick = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val bitmap = getAvattar(100)
        for (i in 0 until bitmap.width) {
            for (j in 0 until bitmap.height) {
                val ball = Ball(i * d + d / 2, j * d + d / 2, bitmap.getPixel(i, j))
                list.add(ball)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                for (ball in list) {
                    ball.vy = rangInt(-15,35)
                    ball.vx = rangInt(-15,35)
                    ball.t = System.currentTimeMillis()
                }
                isClick = !isClick
                invalidate()
            }
        }
        return true
    }

    private fun rangInt(i: Int, j: Int): Int {
        val max = i.coerceAtLeast(j)
        val min = i.coerceAtMost(j) - 1
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (min + Math.ceil(Math.random() * (max - min))).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(200f,200f)
        for (ball in list) {
            paint.color = ball.color
            if (isClick) {
                val nowT = (System.currentTimeMillis() - ball.t) / 1000f
                ball.x = ball.x + (ball.vx * nowT + 0.5f * ball.ax * nowT * nowT).toInt()
                ball.y = ball.y + (ball.vy * nowT + 0.5f * ball.ay * nowT * nowT).toInt()
            }
            canvas.drawCircle(ball.x * 1.0f, ball.y * 1.0f, d / 2f, paint)
        }
        Log.e("cjj", "" + list[0].x + "," + list[0].y)
        if (isClick) {
            invalidate()
        }
    }

    private fun getAvattar(widit: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.woman)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = widit
        return BitmapFactory.decodeResource(resources, R.drawable.woman, options)
    }
}