package com.app.videoapplication.carouel

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RemoteViews.RemoteView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.app.videoapplication.R
import com.app.videoapplication.page.main.Feeds.FeedFragment
import java.util.*


@RemoteView
class CarouselView : FrameLayout {
    private val DEFAULT_GRAVITY = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    private var mPageCount = 0
    private var slideInterval = DEFAULT_SLIDE_INTERVAL
    private var mIndicatorGravity = DEFAULT_GRAVITY
    private var indicatorMarginVertical = 0
    private var indicatorMarginHorizontal = 0
    private var pageTransformInterval = DEFAULT_SLIDE_VELOCITY
    private var indicatorVisibility = DEFAULT_INDICATOR_VISIBILITY
    private lateinit var containerViewPager: CarouselViewPager
    private lateinit var mIndicator: CirclePageIndicator
    private var mViewListener: ViewListener? = null
    private var mImageListener: ImageListener? = null
    private var imageClickListener: ImageClickListener? = null
    private var swipeTimer: Timer? = null
    private var swipeTask: SwipeTask? = null
    var isAutoPlay = false
        private set
    var isDisableAutoPlayOnUserInteraction = false
        private set
    private var animateOnBoundary = true
    private var previousState = 0

    /**
     * Sets page transition animation.
     *
     * @param pageTransformer Choose from zoom, flow, depth, slide_over .
     */
    var pageTransformer: ViewPager.PageTransformer? = null
        set(pageTransformer) {
            field = pageTransformer
            containerViewPager.setPageTransformer(true, pageTransformer)
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        initView(context, attrs, defStyleAttr, 0)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initView(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) {
        if (isInEditMode) {
            return
        } else {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.view_carousel, this, true)
            containerViewPager = view.findViewById(R.id.containerViewPager) as CarouselViewPager
            mIndicator = view.findViewById(R.id.indicator) as CirclePageIndicator
            containerViewPager.addOnPageChangeListener(carouselOnPageChangeListener)


            //Retrieve styles attributes
            val a: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.CarouselView, defStyleAttr, 0)
            try {
                indicatorMarginVertical =
                    a.getDimensionPixelSize(R.styleable.CarouselView_indicatorMarginVertical,
                        resources.getDimensionPixelSize(R.dimen.default_indicator_margin_vertical))
                indicatorMarginHorizontal =
                    a.getDimensionPixelSize(R.styleable.CarouselView_indicatorMarginHorizontal,
                        resources.getDimensionPixelSize(R.dimen.default_indicator_margin_horizontal))
                setPageTransformInterval(a.getInt(R.styleable.CarouselView_pageTransformInterval,
                    DEFAULT_SLIDE_VELOCITY))
                setSlideInterval(a.getInt(R.styleable.CarouselView_slideInterval,
                    DEFAULT_SLIDE_INTERVAL))
                orientation =
                    a.getInt(R.styleable.CarouselView_indicatorOrientation, LinearLayout.HORIZONTAL)
                indicatorGravity = a.getInt(R.styleable.CarouselView_indicatorGravity,
                    Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
                isAutoPlay = a.getBoolean(R.styleable.CarouselView_autoPlay, true)
                isDisableAutoPlayOnUserInteraction =
                    a.getBoolean(R.styleable.CarouselView_disableAutoPlayOnUserInteraction, false)
                setAnimateOnBoundary(a.getBoolean(R.styleable.CarouselView_animateOnBoundary, true))
                setPageTransformer(a.getInt(R.styleable.CarouselView_pageTransformer,
                    CarouselViewPagerTransformer.DEFAULT))
                indicatorVisibility = a.getInt(R.styleable.CarouselView_indicatorVisibility,
                    DEFAULT_INDICATOR_VISIBILITY)
                setIndicatorVisibility(indicatorVisibility)
                if (indicatorVisibility == View.VISIBLE) {
                    var fillColor = a.getColor(R.styleable.CarouselView_fillColor, 0)
                    if (fillColor != 0) {
                        fillColor = fillColor
                    }
                    var pageColor = a.getColor(R.styleable.CarouselView_pageColor, 0)
                    if (pageColor != 0) {
                        pageColor = pageColor
                    }
                    var radius =
                        a.getDimensionPixelSize(R.styleable.CarouselView_radius, 0).toFloat()
                    if (radius != 0f) {
                        radius = radius
                    }
                    isSnap = a.getBoolean(R.styleable.CarouselView_snap,
                        resources.getBoolean(R.bool.default_circle_indicator_snap))
                    var strokeColor = a.getColor(R.styleable.CarouselView_strokeColor, 0)
                    if (strokeColor != 0) {
                        strokeColor = strokeColor
                    }
                    var strokeWidth =
                        a.getDimensionPixelSize(R.styleable.CarouselView_strokeWidth, 0).toFloat()
                    if (strokeWidth != 0f) {
                        strokeWidth = strokeWidth
                    }
                }
            } finally {
                a.recycle()
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        resetScrollTimer()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        playCarousel()
    }

    fun getSlideInterval(): Int {
        return slideInterval
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    fun setSlideInterval(slideInterval: Int) {
        this.slideInterval = slideInterval
        if (null != containerViewPager) {
            playCarousel()
        }
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    fun reSetSlideInterval(slideInterval: Int) {
        setSlideInterval(slideInterval)
        if (null != containerViewPager) {
            playCarousel()
        }
    }

    /**
     * Sets speed at which page will slide from one to another in milliseconds
     *
     * @param pageTransformInterval integer
     */
    fun setPageTransformInterval(pageTransformInterval: Int) {
        if (pageTransformInterval > 0) {
            this.pageTransformInterval = pageTransformInterval
        } else {
            this.pageTransformInterval = DEFAULT_SLIDE_VELOCITY
        }
        containerViewPager.setTransitionVelocity(pageTransformInterval)
    }

    /**
     * Sets page transition animation.
     *
     * @param transformer Pass [CarouselViewPagerTransformer.FLOW], [CarouselViewPagerTransformer.ZOOM], [CarouselViewPagerTransformer.DEPTH] or [CarouselViewPagerTransformer.SLIDE_OVER]
     * @attr
     */
    fun setPageTransformer(@CarouselViewPagerTransformer.Transformer transformer: Int) {
        pageTransformer = CarouselViewPagerTransformer(transformer)
    }

    /**
     * Sets whether to animate transition from last position to first or not.
     *
     * @param animateOnBoundary .
     */
    fun setAnimateOnBoundary(animateOnBoundary: Boolean) {
        this.animateOnBoundary = animateOnBoundary
    }

    private fun setData() {
        val carouselPagerAdapter: CarouselPagerAdapter = CarouselPagerAdapter(
            context)
        containerViewPager.adapter = carouselPagerAdapter
        if (pageCount > 1) {
            mIndicator.setViewPager(containerViewPager)
            mIndicator.requestLayout()
            mIndicator.invalidate()
            containerViewPager.offscreenPageLimit = pageCount
            playCarousel()
        }
    }

    private fun stopScrollTimer() {
        swipeTimer?.cancel()
        swipeTask?.cancel()
    }

    private fun resetScrollTimer() {
        stopScrollTimer()
        swipeTask = SwipeTask()
        swipeTimer = Timer()
    }

    /**
     * Starts auto scrolling if
     */
    fun playCarousel() {
        resetScrollTimer()
        if (isAutoPlay && slideInterval > 0) {
            containerViewPager.adapter?.let {
                if (it.count > 1) {
                    swipeTimer?.schedule(swipeTask, slideInterval.toLong(), slideInterval.toLong())
                }
            }
        }
    }

    /**
     * Pause auto scrolling unless user interacts provided autoPlay is enabled.
     */
    fun pauseCarousel() {
        resetScrollTimer()
    }

    /**
     * Stops auto scrolling.
     */
    fun stopCarousel() {
        isAutoPlay = false
    }

    private inner class CarouselPagerAdapter(context: Context) : PagerAdapter() {
        private val mContext: Context

        init {
            mContext = context
        }

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val objectToReturn: Any

            //Either let user set image to ImageView
            if (mImageListener != null) {
                val imageView = ImageView(mContext)
                imageView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT) //setting image position
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                objectToReturn = imageView
                mImageListener!!.setImageForPosition(position, imageView)
                collection.addView(imageView)

                //Or let user add his own ViewGroup
            } else if (mViewListener != null) {
                val view: View? = mViewListener!!.setViewForPosition(position)
                objectToReturn = view!!
                collection.addView(view)
            } else {
                throw RuntimeException("View must set " + ImageListener::class.java.simpleName + " or " + ViewListener::class.java.simpleName + ".")
            }
            return objectToReturn
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
            return pageCount
        }
    }

    var carouselOnPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
        ) {

            //Programmatic scroll
        }

        override fun onPageSelected(position: Int) {}
        override fun onPageScrollStateChanged(state: Int) {

            //User initiated scroll
            if (previousState == ViewPager.SCROLL_STATE_DRAGGING
                && state == ViewPager.SCROLL_STATE_SETTLING
            ) {
                if (isDisableAutoPlayOnUserInteraction) {
                    pauseCarousel()
                } else {
                    playCarousel()
                }
            } else if (previousState == ViewPager.SCROLL_STATE_SETTLING
                && state == ViewPager.SCROLL_STATE_IDLE
            ) {
            }
            previousState = state
        }
    }

    private inner class SwipeTask : TimerTask() {
        override fun run() {
            containerViewPager.post(Runnable {
                val nextPage: Int = (containerViewPager.currentItem + 1) % pageCount
                containerViewPager.setCurrentItem(nextPage, 0 != nextPage || animateOnBoundary)
            })
        }
    }

    fun setImageListener(mImageListener: ImageListener) {
        this.mImageListener = mImageListener
    }

    fun setViewListener(mViewListener: ViewListener?) {
        this.mViewListener = mViewListener
    }

    fun setImageClickListener(imageClickListener: ImageClickListener?) {
        this.imageClickListener = imageClickListener
        containerViewPager.setImageClickListener(imageClickListener)
    }

    var pageCount: Int
        get() = mPageCount
        set(mPageCount) {
            this.mPageCount = mPageCount
            setData()
        }

    fun addOnPageChangeListener(listener: OnPageChangeListener?) {
        listener?.let { containerViewPager.addOnPageChangeListener(it) }
    }

    fun clearOnPageChangeListeners() {
        containerViewPager.clearOnPageChangeListeners()
    }

    fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        containerViewPager.setCurrentItem(item, smoothScroll)
    }

    var currentItem: Int
        get() = containerViewPager.currentItem
        set(item) {
            containerViewPager.currentItem = item
        }

    fun getIndicatorMarginVertical(): Int {
        return indicatorMarginVertical
    }

    fun setIndicatorMarginVertical(_indicatorMarginVertical: Int) {
        indicatorMarginVertical = _indicatorMarginVertical
        val params = layoutParams as LayoutParams
        params.topMargin = indicatorMarginVertical
        params.bottomMargin = indicatorMarginVertical
    }

    fun getIndicatorMarginHorizontal(): Int {
        return indicatorMarginHorizontal
    }

    fun getContainerViewPager(): CarouselViewPager? {
        return containerViewPager
    }

    fun setIndicatorMarginHorizontal(_indicatorMarginHorizontal: Int) {
        indicatorMarginHorizontal = _indicatorMarginHorizontal
        val params = layoutParams as LayoutParams
        params.leftMargin = indicatorMarginHorizontal
        params.rightMargin = indicatorMarginHorizontal
    }

    var indicatorGravity: Int
        get() = mIndicatorGravity
        set(gravity) {
            mIndicatorGravity = gravity
            val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            params.gravity = mIndicatorGravity
            params.setMargins(indicatorMarginHorizontal,
                indicatorMarginVertical,
                indicatorMarginHorizontal,
                indicatorMarginVertical)
            mIndicator.layoutParams = params
        }

    fun setIndicatorVisibility(visibility: Int) {
        mIndicator.visibility = visibility
    }

    var orientation: Int
        get() = mIndicator.orientation
        set(orientation) {
            mIndicator.orientation=orientation
        }
    var fillColor: Int
        get() = mIndicator.fillColor
        set(fillColor) {
            mIndicator.fillColor=fillColor
        }
    var strokeColor: Int
        get() = mIndicator.strokeColor
        set(strokeColor) {
            mIndicator.strokeColor=strokeColor
        }
    var strokeWidth: Float
        get() = mIndicator.strokeWidth
        set(strokeWidth) {
            mIndicator.strokeWidth=strokeWidth
            val padding = strokeWidth.toInt()
            mIndicator.setPadding(padding, padding, padding, padding)
        }

    override fun setBackground(background: Drawable) {
        super.setBackground(background)
    }

    val indicatorBackground: Drawable
        get() = mIndicator.background
    var pageColor: Int
        get() = mIndicator.pageColor
        set(pageColor) {
            mIndicator.pageColor=pageColor
        }
    var isSnap: Boolean
        get() = mIndicator.isSnap
        set(snap) {
            mIndicator.isSnap=snap
        }
    var radius: Float
        get() = mIndicator.radius
        set(radius) {
            mIndicator.radius=radius
        }

    companion object {
        private const val DEFAULT_SLIDE_INTERVAL = 3500
        private const val DEFAULT_SLIDE_VELOCITY = 400
        const val DEFAULT_INDICATOR_VISIBILITY = 0
    }
}