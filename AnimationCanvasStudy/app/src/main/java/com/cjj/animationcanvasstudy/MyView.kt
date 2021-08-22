package com.cjj.animationcanvasstudy

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ViewAnimator
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val colors = mutableListOf<Int>(
        Color.RED,
        Color.YELLOW,
        Color.GREEN,
        Color.BLUE,
        Color.BLACK,
        Color.CYAN
    )
    private val ballR = 15
    private val ballL = 100f

    private var distance = 100f

    private val angle = 2 * PI
    private var angle1 = 0f

    private var radius = 0f
    private val paint2 = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = Color.WHITE
    }


    private val ballPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    var animator = ValueAnimator.ofFloat(0f, (2 * PI).toFloat()).apply {
        duration = 800
        addUpdateListener {
            angle1 = it.animatedValue as Float
            invalidate()
        }
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                animator2.reverse()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
        repeatCount = 1
        interpolator = LinearInterpolator()
    }

    private val animator2 = ValueAnimator.ofFloat(0f, ballL).apply {
        duration = 2000
        interpolator = OvershootInterpolator(10f)
        addUpdateListener {
            distance = it.animatedValue as Float
            invalidate()
        }
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                animator3.start()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }

    lateinit var animator3: ValueAnimator


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        animator.start()

        animator3 = ValueAnimator.ofFloat(
            0f, hypot(
                (width / 2).toDouble(),
                (height / 2).toDouble()
            ).toFloat()
        ).apply {
            duration = 2000
            addUpdateListener {
                radius = it.animatedValue as Float
                invalidate()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBg(canvas)
    }


    //draw 白色背景
    private fun drawBg(canvas: Canvas) {
        if (radius > 0) {
            val strokeWidth = hypot(
                (width / 2).toDouble(),
                (height / 2).toDouble()
            ).toFloat() - radius
            paint2.strokeWidth = strokeWidth
            canvas.drawCircle(width / 2f, height / 2f, strokeWidth/2 + radius, paint2)
        } else {
            canvas.drawColor(Color.WHITE)
            drawBall(canvas)
        }

    }

    //绘制六个小球
    private fun drawBall(canvas: Canvas) {
        for (index in 0 until colors.size) {
            ballPaint.color = colors[index]
            canvas.drawCircle(
                (width / 2f + cos(index * (angle / colors.size) + angle1) * distance).toFloat(),
                (height / 2f + sin(index * (angle / colors.size) + angle1) * distance).toFloat(),
                ballR * 1.0f,
                ballPaint
            )
        }
    }


}