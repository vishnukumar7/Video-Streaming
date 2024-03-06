package com.app.videoapplication.page

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.app.videoapplication.R
import com.app.videoapplication.page.main.MainApplication
import com.app.videoapplication.page.main.comingSoon.ComingSoonFragment
import com.app.videoapplication.page.main.download.DownloadFragment
import com.app.videoapplication.page.main.feeds.FeedFragment
import com.app.videoapplication.utils.AppRepository
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_main.*

class MainPageActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {

    private val feedFragment= FeedFragment()
    private val comingSoonFragment= ComingSoonFragment()
    private val downloadFragment= DownloadFragment()
    private var activeFragment : Fragment = feedFragment

    private val fragmentFirstDisplay = mutableListOf(false, false, false)

    val appRepository by lazy { (application as MainApplication).appRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.container,downloadFragment,"downloads").hide(downloadFragment)
            add(R.id.container,comingSoonFragment,"Coming Soon").hide(comingSoonFragment)
            add(R.id.container,feedFragment,"Home")
        }.commit()

        bottomNav.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(feedFragment).commit()
                activeFragment = feedFragment
                true
            }

            R.id.coming_soon -> {
                if (!fragmentFirstDisplay[1]) {
                    fragmentFirstDisplay[1] = true
                    comingSoonFragment.onViewStarted()
                }
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(comingSoonFragment).commit()
                activeFragment = comingSoonFragment

                true
            }

            R.id.downloads -> {
                if (!fragmentFirstDisplay[2]) {
                    fragmentFirstDisplay[2] = true
                    downloadFragment.onViewStarted()
                }
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(downloadFragment).commit()
                activeFragment = downloadFragment

                true
            }
            else -> false
        }

    }

    fun onFeedFragmentViewCreated(){
        if (!fragmentFirstDisplay[0]) {
            fragmentFirstDisplay[0] = true
            feedFragment.onViewStarted()
        }
    }


}