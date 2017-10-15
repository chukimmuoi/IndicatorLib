package com.developers.chukimmuoi.indicator

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : IndicatorLib
 * Created by chukimmuoi on 15/10/2017.
 */
class IndicatorView : View, IndicatorInterface, ViewPager.OnPageChangeListener {

    private val DEFAULT_RADIUS_SELECTED   = 20
    private val DEFAULT_RADIUS_UNSELECTED = 15
    private val DEFAULT_DISTANCE          = 40

    private val DEFAULT_ANIMATE_DURATION = 200L

    private var mRadiusSelected   = DEFAULT_RADIUS_SELECTED
    private var mRadiusUnselected = DEFAULT_RADIUS_UNSELECTED

    private var mColorSelected   = 0
    private var mColorUnselected = 0

    private var mDistance = DEFAULT_DISTANCE

    private lateinit var mDots: Array<Dot>

    private lateinit var mViewPager: ViewPager

    private var mCurrentPosition = 0

    private var mBeforePosition  = 0

    private var mAnimateDuration = DEFAULT_ANIMATE_DURATION

    private lateinit var mAnimatorZoomIn: ValueAnimator
    private lateinit var mAnimatorZoomOut: ValueAnimator

    constructor(context: Context?) : super(context)

    constructor(context: Context,
                attrs: AttributeSet) : super(context, attrs) {
        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)

        this.mRadiusSelected = typeArray.getDimensionPixelSize(
                R.styleable.IndicatorView_indicator_radius_selected,
                DEFAULT_RADIUS_SELECTED)

        this.mRadiusUnselected = typeArray.getDimensionPixelSize(
                R.styleable.IndicatorView_indicator_color_unselected,
                DEFAULT_RADIUS_UNSELECTED)

        this.mColorSelected = typeArray.getColor(
                R.styleable.IndicatorView_indicator_color_selected,
                Color.WHITE)

        this.mColorUnselected = typeArray.getColor(
                R.styleable.IndicatorView_indicator_color_unselected,
                Color.WHITE)

        this.mDistance = typeArray.getInt(
                R.styleable.IndicatorView_indicator_distance,
                DEFAULT_DISTANCE)

        typeArray.recycle()
    }

    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var desiredHeight = 2 * mRadiusSelected

        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var width = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                widthSize
            }
            MeasureSpec.AT_MOST -> {
                widthSize
            }
            else -> 0
        }

        var height = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize
            }
            MeasureSpec.AT_MOST -> {
                Math.min(desiredHeight, heightSize)
            }
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        var yCenter = (height / 2).toFloat()

        var d = mDistance + 2 * mRadiusUnselected

        var firstXCenter = ((width / 2) - ((mDots.size - 1) * d / 2)).toFloat()

        for (i in 0 until mDots.size) {
            mDots[i].setCenter(
                    if (i == 0) firstXCenter else (firstXCenter + d * i), yCenter
            )
            mDots[i].setCurrentRadius(
                    if (i == mCurrentPosition) mRadiusSelected else mRadiusUnselected
            )
            mDots[i].setColor(
                    if (i == mCurrentPosition) mColorSelected else mColorUnselected
            )
            mDots[i].setAlpha(
                    if (i == mCurrentPosition) 255 else mRadiusUnselected * 255 / mRadiusSelected
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until mDots.size) {
            mDots[i].draw(canvas)
        }
    }

    @Throws(PagesLessException::class) override fun setViewpager(viewPager: ViewPager) {
        this.mViewPager = viewPager
        viewPager.addOnPageChangeListener(this)
        initDot(viewPager.adapter.count)

        this.mCurrentPosition = viewPager.currentItem
        onPageSelected(mCurrentPosition)
    }

    @Throws(PagesLessException::class) private fun initDot(count: Int) {
        if (count < 2) throw PagesLessException()

        mDots = Array(count, { _ -> Dot() })
    }

    override fun setAnimateDuration(duration: Long) {
        this.mAnimateDuration = duration
    }

    override fun setRadiusSelected(radius: Int) {
        this.mRadiusSelected = radius
    }

    override fun setRadiusUnselected(radius: Int) {
        this.mRadiusUnselected = radius
    }

    override fun setDistanceDot(distance: Int) {
        this.mDistance = distance
    }

    private fun changeNewRadius(positionPerform: Int, newRadius: Int) {
        val dot = mDots[positionPerform]
        if (dot.getCurrentRadius() != newRadius) {
            dot.setCurrentRadius(newRadius)
            dot.setAlpha(newRadius * 255 / mRadiusSelected)

            invalidate()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        // Not use.
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //Not use.
    }

    override fun onPageSelected(position: Int) {
        mBeforePosition = mCurrentPosition
        mCurrentPosition = position

        if (mBeforePosition == mCurrentPosition) {
            mBeforePosition = mCurrentPosition + 1
        }

        mDots[mCurrentPosition].setColor(mColorSelected)
        mDots[mBeforePosition].setColor(mColorUnselected)

        var animatorSet = AnimatorSet()
        animatorSet.duration = mAnimateDuration

        mAnimatorZoomIn = ValueAnimator.ofInt(mRadiusUnselected, mRadiusSelected)
        mAnimatorZoomIn.addUpdateListener { animation ->
            var positionPerform = mCurrentPosition
            run {
                val newRadius = animation.animatedValue as Int
                changeNewRadius(positionPerform, newRadius)
            }
        }

        mAnimatorZoomOut = ValueAnimator.ofInt(mRadiusSelected, mRadiusUnselected)
        mAnimatorZoomOut.addUpdateListener { animation ->
            var positionPerform = mBeforePosition
            run {
                val newRadius = animation.animatedValue as Int
                changeNewRadius(positionPerform, newRadius)
            }
        }

        animatorSet.play(mAnimatorZoomIn).with(mAnimatorZoomOut)
        animatorSet.start()
    }
}