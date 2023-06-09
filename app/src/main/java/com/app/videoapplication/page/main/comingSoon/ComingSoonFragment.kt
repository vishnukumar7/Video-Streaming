package com.app.videoapplication.page.main.comingSoon

import androidx.lifecycle.ViewModelProvider
import com.app.videoapplication.NextPageListener
import com.app.videoapplication.R
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.page.main.BaseFragment
import com.faltenreich.skeletonlayout.Skeleton
import kotlinx.android.synthetic.main.fragment_coming_soon.*

class ComingSoonFragment : BaseFragment(R.layout.fragment_coming_soon), NextPageListener {

    override lateinit var skeleton: Skeleton

    lateinit var comingSoonViewModel: ComingSoonViewModel
    var listItem = ArrayList<ResultsItem>()
    var adapter = ComingSoonAdapter(listItem,this)
    var lastPosition=0
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