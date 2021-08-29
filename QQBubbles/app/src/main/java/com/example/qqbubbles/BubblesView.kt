package com.example.qqbubbles

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.PointFEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import kotlin.math.hypot

class BubblesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mBallRadius = 40f
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

    val mBitmapList = mutableListOf<Bitmap>()


    private val mFontMetrics = mDrawTextPaint.fontMetrics
    private val mText = "12"

    //移动时坐标
    private var mConnectX = 0f
    private var mConnectY = 0f

    private val mOverMove = 400f


    private lateinit var mReboundAnimator: ValueAnimator
    private lateinit var mBubblesAnimator: ValueAnimator

    private var mBitmapIndex = 0

    private val pointCenter = PointF()
    private val pointA: PointF = PointF()
    private val pointD: PointF = PointF()
    private val pointB: PointF = PointF()
    private val pointC: PointF = PointF()
    private val pathLine: Path = Path()


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmapList.clear()
        mBitmapList.add(BitmapFactory.decodeResource(resources, R.mipmap.burst_1))
        mBitmapList.add(BitmapFactory.decodeResource(resources, R.mipmap.burst_2))
        mBitmapList.add(BitmapFactory.decodeResource(resources, R.mipmap.burst_3))
        mBitmapList.add(BitmapFactory.decodeResource(resources, R.mipmap.burst_4))
        mBitmapList.add(BitmapFactory.decodeResource(resources, R.mipmap.burst_5))

        mBubblesAnimator = ValueAnimator.ofInt(0, mBitmapList.size).apply {
            addUpdateListener {
                mBitmapIndex = it.animatedValue as Int
                invalidate()
            }
            duration = 500
        }

        mReboundAnimator = ValueAnimator.ofObject(
            PointFEvaluator(),
            PointF(mConnectX, mConnectY),
            PointF(width / 2f, height / 2f)
        ).apply {
            duration = 400
            interpolator = OvershootInterpolator(5f)
            addUpdateListener {
                mConnectX = (it.animatedValue as PointF).x
                mConnectY = (it.animatedValue as PointF).y
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(p0: Animator?) {
                    invalidate()
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
                    mBubblesState = BubblesState.Boom
                    mBubblesAnimator.start()
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
                val smallR = (mBallRadius - (hypot(
                    mConnectX - width / 2,
                    mConnectY - height / 2,
                ) / mOverMove) * mBallRadius).coerceAtLeast(10f)
                pointCenter.x = (mConnectX + width / 2) / 2
                pointCenter.y = (mConnectY + height / 2) / 2
                val cosL =
                    (height / 2 - mConnectY) / hypot(mConnectY - height / 2, mConnectX - width / 2)
                val sinL =
                    (mConnectX - width / 2) / hypot(mConnectY - height / 2, mConnectX - width / 2)
                pointA.x = width / 2f - cosL * smallR
                pointA.y = height / 2f - sinL * smallR

                pointD.x = width / 2f + cosL * smallR
                pointD.y = height / 2f + sinL * smallR

                pointB.x = mConnectX - cosL * mBallRadius
                pointB.y = mConnectY - sinL * mBallRadius
                pointC.x = mConnectX + cosL * mBallRadius
                pointC.y = mConnectY + sinL * mBallRadius


                pathLine.reset()
                pathLine.moveTo(pointD.x, pointD.y)
                pathLine.quadTo(pointCenter.x, pointCenter.y, pointC.x, pointC.y)

                pathLine.lineTo(pointB.x, pointB.y)
                pathLine.quadTo(pointCenter.x, pointCenter.y, pointA.x, pointA.y)
                pathLine.close()

                //todo 绘制贝塞尔曲线
                canvas.drawPath(pathLine, mPaint)
                canvas.drawCircle(
                    width / 2f, height / 2f, smallR, mPaint
                )

                //todo 绘制动球
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
                if (mBitmapIndex < mBitmapList.size)
                    canvas.drawBitmap(mBitmapList[mBitmapIndex], mConnectX, mConnectY, mPaint)
            }
        }
        super.onDraw(canvas)

    }
}