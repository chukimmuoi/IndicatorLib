package com.developers.chukimmuoi.indicator

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : IndicatorLib
 * Created by chukimmuoi on 15/10/2017.
 */
class Dot {

    private var mPaint = Paint()

    private var mCenter = PointF()

    private var mCurrentRadius: Float = 0.0F

    init {
        mPaint.isAntiAlias = true
    }

    fun setColor(color: Int) {
        mPaint.color = color
    }

    fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    fun setCenter(x: Float, y: Float) {
        mCenter.set(x, y)
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(mCenter.x, mCenter.y, mCurrentRadius, mPaint)
    }

    fun setCurrentRadius(radius: Float) {
        mCurrentRadius = radius
    }

    fun getCurrentRadius(): Float {
        return mCurrentRadius
    }
}