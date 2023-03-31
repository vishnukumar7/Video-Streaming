package com.app.videoapplication.page.main.comingSoon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.videoapplication.NextPageListener
import com.app.videoapplication.R
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.page.main.BaseFragment
import kotlinx.android.synthetic.main.fragment_coming_soon.*
import kotlinx.android.synthetic.main.fragment_feed.*

class ComingSoonFragment : BaseFragment(), NextPageListener {

    lateinit var comingSoonViewModel: ComingSoonViewModel
    var listItem = ArrayList<ResultsItem>()
    var adapter = ComingSoonAdapter(listItem,this)
    var lastPosition=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false)
    }

    override fun onViewStarted() {
        super.onViewStarted()
        comingSoonViewModel = ViewModelProvider(this)[ComingSoonViewModel::class.java]
        comingSoonViewModel.resultsItem.observe(viewLifecycleOwner) {
            if(adapter.listItem.size==0){
                adapter.listItem.addAll(it)
                adapter.notifyDataSetChanged()
            }else{
                adapter.listItem.addAll(it)
                adapter.notifyItemChanged(lastPosition)
            }

        }
        comingSoonViewModel.start()

        comingRecyclerView.adapter=adapter
    }

    override fun onNextPageCall(position: Int) {
        lastPosition=position
        comingSoonViewModel.nextPage()
    }
}