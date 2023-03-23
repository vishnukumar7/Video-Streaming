package com.app.videoapplication.page.main.Feeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.AppUtils.getImageUrl
import com.app.videoapplication.AppUtils.optString
import com.app.videoapplication.ImageSize
import com.app.videoapplication.R
import com.app.videoapplication.carouel.ImageListener
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.model.TvShowResultsItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.feed_list_view.view.*
import kotlinx.android.synthetic.main.home_header_view.view.*

class FeedAdapter(val feedItemList: ArrayList<FeedItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> CarouselViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_header_view,parent,false))
            else -> FeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.feed_list_view,parent,false))
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(position==0)
            1
        else 2
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FeedViewHolder -> {
                holder.bind(holder.itemView,feedItemList[position])
            }
            is CarouselViewHolder -> {
                holder.bind(holder.itemView,feedItemList[position].itemList as ArrayList<ResultsItem>)
            }
        }
    }

    override fun getItemCount(): Int {
        return feedItemList.size
    }

}

class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    private val imageAdapter=FeedImageAdapter()
    private val imageTvShowAdapter = FeedImageTvShowAdapter()
    fun bind(itemView: View,feedItem : FeedItem){
        itemView.feedTitle.text=feedItem.title
        if(feedItem.itemList.isNotEmpty() && feedItem.itemList[0] is ResultsItem){
            imageAdapter.resultItemList.clear()
            imageAdapter.resultItemList.addAll(feedItem.itemList as ArrayList<ResultsItem>)
            itemView.feedListRecyclerView.adapter=imageAdapter
        }else if(feedItem.itemList.isNotEmpty() && feedItem.itemList[0] is TvShowResultsItem){
            imageTvShowAdapter.resultItemList.clear()
            imageTvShowAdapter.resultItemList.addAll(feedItem.itemList as ArrayList<TvShowResultsItem>)
            itemView.feedListRecyclerView.adapter=imageTvShowAdapter
        }


    }
}

class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val carouselList= ArrayList<ResultsItem>()
    fun bind(itemView: View,resultsItem: ArrayList<ResultsItem>){
        carouselList.clear()
        carouselList.addAll(resultsItem.take(10))
        itemView.carouselView.pageCount=carouselList.size
        itemView.carouselView.setImageListener(imageListener)
    }
    val imageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            imageView?.let {
                val url = carouselList[position].backdropPath.optString().ifEmpty { carouselList[position].posterPath }
                Glide.with(itemView.context).load(url.optString().getImageUrl(ImageSize.ORIGINAL)).into(
                    it)
            }
        }
    }

}