package com.app.videoapplication.page.main.feeds

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.app.videoapplication.ClickHandlerListener
import com.app.videoapplication.R
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.page.MainPageActivity
import com.app.videoapplication.page.main.BaseFragment
import com.app.videoapplication.page.main.details.MovieDetailActivity
import com.app.videoapplication.page.main.listPage.ListActivity
import com.app.videoapplication.utils.AppViewModelFactory
import com.app.videoapplication.utils.Constants
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.fragment_feed.close
import kotlinx.android.synthetic.main.fragment_feed.feedRecyclerView
import kotlinx.android.synthetic.main.fragment_feed.movie
import kotlinx.android.synthetic.main.fragment_feed.selectedView
import kotlinx.android.synthetic.main.fragment_feed.tv

class FeedFragment : BaseFragment(R.layout.fragment_feed), ClickHandlerListener,
    View.OnClickListener {

    override lateinit var skeleton: Skeleton

    private val feedViewModel: FeedViewModel by viewModels {
        AppViewModelFactory((activity as MainPageActivity).appRepository)
    }
    private val feedList = ArrayList<FeedItem>()
    private val feedAdapter = FeedAdapter(feedList, this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainPageActivity).onFeedFragmentViewCreated()
        feedRecyclerView.adapter = feedAdapter
        skeleton =
            feedRecyclerView.applySkeleton(R.layout.feed_list_view, 10).apply { showSkeleton() }

    }

    override fun onViewStarted() {
        feedViewModel.getFeeds()
        feedViewModel.resultsItem.observe(viewLifecycleOwner) {
            feedList.clear()

            feedList.addAll(it.sortedBy { it.list })
            skeleton.showOriginal()
            feedAdapter.notifyDataSetChanged()
        }

        movie.setOnClickListener(this)
        tv.setOnClickListener(this)
        close.setOnClickListener(this)


    }

    override fun viewAll(position: Int, name: String) {
        val intent = Intent(requireContext(), ListActivity::class.java)
        intent.putExtra("title", name)
        startActivity(intent)
    }

    override fun detailPage(movieId: String) {
        val intent = Intent(requireContext(),MovieDetailActivity::class.java)
        intent.putExtra(Constants.MOVIE_ID,movieId)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.movie -> {
                movie.visibility = View.GONE
                tv.visibility = View.GONE
                selectedView.text = getString(R.string.movie)
                feedViewModel.type = "Movie"
                selectedView.visibility = View.VISIBLE
                close.visibility = View.VISIBLE
                feedViewModel.getMovieFeed()
            }

            R.id.tv -> {
                movie.visibility = View.GONE
                tv.visibility = View.GONE
                selectedView.text = getString(R.string.tv)
                feedViewModel.type = "Tv"
                selectedView.visibility = View.VISIBLE
                close.visibility = View.VISIBLE
                feedViewModel.getTvFeed()
            }

            R.id.close -> {
                movie.visibility = View.VISIBLE
                tv.visibility = View.VISIBLE
                selectedView.text = getString(R.string.movie)
                feedViewModel.type = "All"
                selectedView.visibility = View.GONE
                close.visibility = View.GONE
                feedViewModel.getFeeds()
            }
        }
    }


}