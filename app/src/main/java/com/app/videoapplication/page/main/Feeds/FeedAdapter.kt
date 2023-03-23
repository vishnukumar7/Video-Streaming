package com.app.videoapplication.page.main.Feeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.R
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.model.ResultsItem
import kotlinx.android.synthetic.main.feed_list_view.view.*

class FeedAdapter(val feedItemList: ArrayList<FeedItem>) : RecyclerView.Adapter<FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.feed_list_view,parent,false))
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(holder.itemView,feedItemList[position])
        println("//feed holder : ${feedItemList.get(position).title}")
    }

    override fun getItemCount(): Int {
        return feedItemList.size
    }

}

class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    val imageAdapter=FeedImageAdapter()
    fun bind(itemView: View,feedItem : FeedItem){
        itemView.feedTitle.text=feedItem.title
        imageAdapter.resultItemList.clear()
        imageAdapter.resultItemList.addAll(feedItem.itemList)
        itemView.feedListRecyclerView.adapter=imageAdapter

    }
}