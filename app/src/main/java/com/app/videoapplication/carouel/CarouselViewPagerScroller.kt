package com.app.videoapplication.carouel

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller


class CarouselViewPagerScroller : Scroller {
    private var mScrollDuration = 600

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, interpolator: Interpolator?) : super(context, interpolator) {}

    fun getmScrollDuration(): Int {
        return mScrollDuration
    }

    fun setmScrollDuration(mScrollDuration: Int) {
        this.mScrollDuration = mScrollDuration
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration)
    }
}