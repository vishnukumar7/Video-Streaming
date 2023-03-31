package com.app.videoapplication.page.main

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.app.videoapplication.ConfigurationListener
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes),ConfigurationListener {

    abstract var skeleton: Skeleton
    open fun onViewStarted() {

    }

    override fun onMaskColorChanged(value: Int) {
        skeleton.maskColor = value
    }

    override fun onMaskCornerRadiusChanged(value: Float) {
        skeleton.maskCornerRadius = value
    }

    override fun onShowShimmerChanged(value: Boolean) {
        skeleton.showShimmer = value
    }

    override fun onShimmerColorChanged(value: Int) {
        skeleton.shimmerColor = value
    }

    override fun onShimmerDurationChanged(value: Long) {
        skeleton.shimmerDurationInMillis = value
    }

    override fun onShimmerDirectionChanged(value: Int) {
        skeleton.shimmerDirection = SkeletonShimmerDirection.valueOf(value) ?: SkeletonShimmerDirection.LEFT_TO_RIGHT
    }

    override fun onShimmerAngleChanged(value: Int) {
        skeleton.shimmerAngle = value
    }
}