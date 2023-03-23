package com.app.videoapplication.page.main.Feeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.AppUtils.getImageUrl
import com.app.videoapplication.AppUtils.optString
import com.app.videoapplication.BuildConfig
import com.app.videoapplication.ImageSize
import com.app.videoapplication.R
import com.app.videoapplication.carouel.ImageListener
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.model.TvShowResultsItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.home_header_view.view.*
import kotlinx.android.synthetic.main.list_feed.view.*

class FeedImageAdapter : RecyclerView.Adapter<FeedImageViewHolder>() {

    val resultItemList= ArrayList<ResultsItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedImageViewHolder {
        return FeedImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_feed,parent,false))
    }


    override fun onBindViewHolder(holder: FeedImageViewHolder, position: Int) {
        holder.bind(holder.itemView,resultItemList[position])
    }

    override fun getItemCount(): Int {
        return resultItemList.size
    }

}

class FeedImageTvShowAdapter : RecyclerView.Adapter<FeedImageViewHolder>() {

    val resultItemList= ArrayList<TvShowResultsItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedImageViewHolder {
        return FeedImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_feed,parent,false))
    }

    override fun onBindViewHolder(holder: FeedImageViewHolder, position: Int) {
        holder.bind(holder.itemView,resultItemList[position])
    }

    override fun getItemCount(): Int {
        return resultItemList.size
    }

}

class FeedImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(itemView: View,resultsItem: TvShowResultsItem){
        Glide.with(itemView.context).load(resultsItem.posterPath.optString().getImageUrl()).into(itemView.feedImageView)
    }

    fun bind(itemView: View,resultsItem: ResultsItem){
        Glide.with(itemView.context).load(resultsItem.posterPath.optString().getImageUrl()).into(itemView.feedImageView)
    }
}


