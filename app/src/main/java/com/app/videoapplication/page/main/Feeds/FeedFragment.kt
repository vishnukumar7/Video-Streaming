package com.app.videoapplication.page.main.Feeds

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.app.videoapplication.AppUtils.getImageUrl
import com.app.videoapplication.AppUtils.optString
import com.app.videoapplication.ClickViewAllListener
import com.app.videoapplication.ImageSize
import com.app.videoapplication.R
import com.app.videoapplication.Utils.Constants
import com.app.videoapplication.carouel.ImageListener
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.page.MainPageActivity
import com.app.videoapplication.page.main.BaseFragment
import com.app.videoapplication.page.main.listPage.ListActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_feed.*

open class FeedFragment : BaseFragment(), ClickViewAllListener {

    lateinit var feedViewModel: FeedViewModel
    private val feedList = ArrayList<FeedItem>()
    private val feedAdapter= FeedAdapter(feedList,this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedViewModel= ViewModelProvider(this)[FeedViewModel::class.java]
        (activity as MainPageActivity).onFeedFragmentViewCreated()
        feedRecyclerView.adapter=feedAdapter

    }

    override fun onViewStarted() {
        feedViewModel.getFeeds()
        feedViewModel.resultsItem.observe(viewLifecycleOwner){
            feedList.clear()
            feedList.addAll(it)
            feedAdapter.notifyDataSetChanged()
        }
    }

    override fun viewAll(position: Int, name: String) {
        if (name == Constants.POPULAR || name==Constants.TOP_RATED) {
            val intent=Intent(requireContext(),ListActivity::class.java)
            intent.putExtra("title",name)
            startActivity(intent)
        }
    }


}