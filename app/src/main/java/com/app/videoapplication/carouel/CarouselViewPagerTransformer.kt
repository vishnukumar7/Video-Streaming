package com.app.videoapplication.carouel

import android.view.View
import androidx.annotation.IntDef
import androidx.viewpager.widget.ViewPager


class CarouselViewPagerTransformer internal constructor(private val mTransformType: Int) :
    ViewPager.PageTransformer {
    /** @hide
     */
    @IntDef(FLOW, SLIDE_OVER, DEPTH, ZOOM, DEFAULT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Transformer

    override fun transformPage(page: View, position: Float) {
        val alpha: Float
        val scale: Float
        val translationX: Float
        when (mTransformType) {
            FLOW -> {
                page.setRotationY(position * -30f)
                return
            }
            SLIDE_OVER -> if (position < 0 && position > -1) {
                // this is the page to the left
                scale =
                    Math.abs(Math.abs(position) - 1) * (1.0f - SCALE_FACTOR_SLIDE) + SCALE_FACTOR_SLIDE
                alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position))
                val pageWidth: Int = page.getWidth()
                val translateValue = position * -pageWidth
                translationX = if (translateValue > -pageWidth) {
                    translateValue
                } else {
                    0f
                }
            } else {
                alpha = 1f
                scale = 1f
                translationX = 0f
            }
            DEPTH -> if (position > 0 && position < 1) {
                // moving to the right
                alpha = 1 - position
                scale = MIN_SCALE_DEPTH + (1 - MIN_SCALE_DEPTH) * (1 - Math.abs(position))
                translationX = page.getWidth() * -position
            } else {
                // use default for all other cases
                alpha = 1f
                scale = 1f
                translationX = 0f
            }
            ZOOM -> if (position >= -1 && position <= 1) {
                scale = Math.max(MIN_SCALE_ZOOM, 1 - Math.abs(position))
                alpha = MIN_ALPHA_ZOOM +
                        (scale - MIN_SCALE_ZOOM) / (1 - MIN_SCALE_ZOOM) * (1 - MIN_ALPHA_ZOOM)
                val vMargin: Float = page.getHeight() * (1 - scale) / 2
                val hMargin: Float = page.getWidth() * (1 - scale) / 2
                translationX = if (position < 0) {
                    hMargin - vMargin / 2
                } else {
                    -hMargin + vMargin / 2
                }
            } else {
                alpha = 1f
                scale = 1f
                translationX = 0f
            }
            else -> return
        }
        page.setAlpha(alpha)
        page.setTranslationX(translationX)
        page.setScaleX(scale)
        page.setScaleY(scale)
    }

    companion object {
        const val FLOW = 0
        const val SLIDE_OVER = 1
        const val DEPTH = 2
        const val ZOOM = 3
        const val DEFAULT = -1
        private const val MIN_SCALE_DEPTH = 0.75f
        private const val MIN_SCALE_ZOOM = 0.85f
        private const val MIN_ALPHA_ZOOM = 0.5f
        private const val SCALE_FACTOR_SLIDE = 0.85f
        private const val MIN_ALPHA_SLIDE = 0.35f
    }
}