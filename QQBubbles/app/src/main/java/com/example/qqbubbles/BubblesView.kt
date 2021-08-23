package com.example.qqbubbles

import android.animation.Animator
import android.animation.PointFEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import kotlin.math.hypot

class BubblesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mBallRadius = 30f
    private var mBubblesState = BubblesState.Default
    private val mPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = true
    }
    private val mDrawTextPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        isAntiAlias = true
        textSize = 35f
        textAlign = Paint.Align.CENTER
    }


    private val mFontMetrics = mDrawTextPaint.fontMetrics
    private val mText = "12"

    //移动时坐标
    private var mConnectX = 0f
    private var mConnectY = 0f

    private val mOverMove = 300f


    private lateinit var mReboundAnimator: ValueAnimator

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mReboundAnimator = ValueAnimator.ofObject(
            PointFEvaluator(),
            PointF(mConnectX, mConnectY),
            PointF(width / 2f, height / 2f)
        ).apply {
            duration = 400
            interpolator = OvershootInterpolator()
            addUpdateListener {
                mConnectX = (it.animatedValue as PointF).x
                mConnectY = (it.animatedValue as PointF).y
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    mBubblesState = BubblesState.Default
                    invalidate()
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationRepeat(p0: Animator?) {

                }

            })
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x > (width / 2 - mBallRadius) && event.x < (width / 2 + mBallRadius) &&
                    event.y > (height / 2 - mBallRadius) && event.y < (height / 2 + mBallRadius)
                ) {
                    mBubblesState = BubblesState.Connect
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mBubblesState == BubblesState.Connect || mBubblesState == BubblesState.OverMove) {
                    mConnectX = event.x
                    mConnectY = event.y
                    if (hypot(
                            mConnectX - width / 2,
                            mConnectY - height / 2,
                        ) > mOverMove
                    ) {
                        mBubblesState = BubblesState.OverMove
                    }
                    invalidate()
                }

            }
            MotionEvent.ACTION_UP -> {
                if (mBubblesState == BubblesState.Connect) {
                    // todo 执行回弹动画
                    mReboundAnimator.setObjectValues(
                        PointF(mConnectX, mConnectY),
                        PointF(width / 2f, height / 2f)
                    )
                    mReboundAnimator.start()
                } else if (mBubblesState == BubblesState.OverMove) {
                    // todo 执行爆炸动画
                }
            }
        }

        return true
    }

    fun reset() {
        mBubblesState = BubblesState.Default
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        when (mBubblesState) {
            BubblesState.Default -> {
                canvas.drawCircle(width / 2f, height / 2f, mBallRadius, mPaint)
                canvas.drawText(
                    mText,
                    width / 2f,
                    height / 2f - (mFontMetrics.ascent + mFontMetrics.bottom) / 2,
                    mDrawTextPaint
                )
            }
            BubblesState.Connect -> {
                //todo 小球不断缩小
                canvas.drawCircle(
                    width / 2f, height / 2f, (mBallRadius - (hypot(
                        mConnectX - width / 2,
                        mConnectY - height / 2,
                    ) / mOverMove) * mBallRadius).coerceAtLeast(10f), mPaint
                )

                //todo 绘制贝塞尔曲线
                canvas.drawCircle(mConnectX, mConnectY, mBallRadius, mPaint)
                canvas.drawText(
                    mText,
                    mConnectX,
                    mConnectY - (mFontMetrics.ascent + mFontMetrics.bottom) / 2,
                    mDrawTextPaint
                )
            }
            BubblesState.OverMove -> {
                canvas.drawCircle(mConnectX, mConnectY, mBallRadius, mPaint)
                canvas.drawText(
                    mText,
                    mConnectX,
                    mConnectY - (mFontMetrics.ascent + mFontMetrics.bottom) / 2,
                    mDrawTextPaint
                )
            }
            BubblesState.Boom -> {

            }
        }
        super.onDraw(canvas)

    }
}