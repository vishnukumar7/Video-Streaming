package com.app.videoapplication.page.main.comingSoon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.videoapplication.R
import com.app.videoapplication.page.main.BaseFragment

open class ComingSoonFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false)
    }
}