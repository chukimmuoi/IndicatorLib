package com.developers.chukimmuoi.indicatorlib

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.developers.chukimmuoi.indicator.IndicatorView
import com.developers.chukimmuoi.indicator.PagesLessException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var mIndicatorView: IndicatorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = viewPager
        mIndicatorView = indicator

        var pagerAdapter = PagerAdapter(applicationContext)
        mViewPager.adapter = pagerAdapter
        try {
            mIndicatorView.setViewpager(mViewPager)
        } catch (e: PagesLessException) {
            e.printStackTrace()
        }
    }
}
