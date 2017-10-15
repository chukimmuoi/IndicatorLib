package com.developers.chukimmuoi.indicatorlib

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : IndicatorLib
 * Created by chukimmuoi on 15/10/2017.
 */
class PagerAdapter constructor(private val mContext: Context) : PagerAdapter() {

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return 5
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var inflater = LayoutInflater.from(mContext)
        var layout = inflater.inflate(R.layout.page, container, false)
        when (position) {
            0 -> {
                layout.setBackgroundColor(mContext.resources.getColor(R.color.color0))
            }
            1 -> {
                layout.setBackgroundColor(mContext.resources.getColor(R.color.color1))
            }
            2 -> {
                layout.setBackgroundColor(mContext.resources.getColor(R.color.color2))
            }
            3 -> {
                layout.setBackgroundColor(mContext.resources.getColor(R.color.color3))
            }
            4 -> {
                layout.setBackgroundColor(mContext.resources.getColor(R.color.color4))
            }
        }

        container?.addView(layout)
        return layout
    }
}