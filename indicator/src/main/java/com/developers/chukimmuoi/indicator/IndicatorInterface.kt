package com.developers.chukimmuoi.indicator

import android.support.v4.view.ViewPager

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : IndicatorLib
 * Created by chukimmuoi on 15/10/2017.
 */
interface IndicatorInterface {

    @Throws(PagesLessException::class)
    fun setViewpager(viewPager: ViewPager)

    fun setAnimateDuration(duration: Long)

    /**
     * @param radius: radius in pixel
     * */
    fun setRadiusSelected(radius: Int)

    /**
     * @param radius: radius in pixel
     * */
    fun setRadiusUnselected(radius: Int)

    /**
     * @param distance: distance in pixel
     * */
    fun setDistanceDot(distance: Int)
}