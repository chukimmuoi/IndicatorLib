package com.developers.chukimmuoi.indicator

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : IndicatorLib
 * Created by chukimmuoi on 15/10/2017.
 */
class PagesLessException: Exception() {

    override val message: String?
        get() = "Pages must equal or larger than 2"
}