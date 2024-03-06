package com.app.videoapplication.page.main.listPage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.app.videoapplication.utils.AppUtils.optString
import com.app.videoapplication.NextPageListener
import com.app.videoapplication.R
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.page.BaseActivity
import com.app.videoapplication.page.main.MainApplication
import com.app.videoapplication.page.main.feeds.ALL_ITEM
import com.app.videoapplication.page.main.feeds.FeedImageAdapter
import com.app.videoapplication.utils.AppViewModelFactory
import kotlinx.android.synthetic.main.header_view.*
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : BaseActivity(), NextPageListener {

    private var listItem= ArrayList<ResultsItem>()
    private var adapter = FeedImageAdapter(this,listItem,true, nextPageListener = this, type = ALL_ITEM)

    private val listViewModel : ListViewModel by viewModels {
        AppViewModelFactory((application as MainApplication).appRepository)
    }
    private var lastPosition=0
    private var titleString=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)
        titleString=intent.getStringExtra("title").optString()
        listRecyclerView.adapter=adapter
        headerTitle.text=titleString
        listViewModel.start(titleString)
        listViewModel.resultsItem.observe(this){
            if(adapter.listList.size==0){
                adapter.listList.addAll(it)
                adapter.notifyDataSetChanged()
            }else{
                adapter.listList.addAll(it)
                adapter.notifyItemChanged(lastPosition)
            }
        }
    }

    override fun onNextPageCall(position: Int) {
        listViewModel.nextPage()
        lastPosition=position
    }
}