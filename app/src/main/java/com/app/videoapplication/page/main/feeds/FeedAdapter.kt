package com.app.videoapplication.page.main.feeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.ClickHandlerListener
import com.app.videoapplication.R
import com.app.videoapplication.carouel.ImageListener
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.utils.AppUtils.optString
import com.app.videoapplication.utils.AppUtils.toPosterUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlinx.android.synthetic.main.feed_list_view.view.feedListRecyclerView
import kotlinx.android.synthetic.main.feed_list_view.view.feedTitle
import kotlinx.android.synthetic.main.feed_list_view.view.viewAll
import kotlinx.android.synthetic.main.home_header_view.view.carouselView

class FeedAdapter(
    val feedItemList: ArrayList<FeedItem>,
    var clickViewAllListener: ClickHandlerListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> CarouselViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_header_view, parent, false)
            )

            else -> FeedViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.feed_list_view, parent, false)
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            1
        else 2
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedViewHolder -> {
                holder.bind(holder.itemView, feedItemList[position], position, clickViewAllListener)
            }

            is CarouselViewHolder -> {
                holder.bind(
                    holder.itemView,
                    feedItemList[position].itemList as ArrayList<ResultsItem>
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return feedItemList.size
    }

}

class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ImageClickListener {

    private val imageAdapter = FeedImageAdapter(itemView.context, imageClickListener = this)

    private var clickListener: ClickHandlerListener? = null
    fun bind(
        itemView: View,
        feedItem: FeedItem,
        position: Int,
        clickViewAllListener: ClickHandlerListener?
    ) {
        clickListener = clickViewAllListener
        itemView.feedTitle.text = feedItem.title
        itemView.viewAll.visibility = if (feedItem.viewAll) View.VISIBLE else View.GONE
        itemView.viewAll.setOnClickListener {
            clickViewAllListener?.viewAll(
                position,
                feedItem.title
            )
        }

        if (feedItem.itemList.isNotEmpty() && feedItem.itemList[0] is ResultsItem) {
            imageAdapter.listList.clear()
            imageAdapter.listList.addAll(feedItem.itemList as ArrayList<ResultsItem>)
            itemView.feedListRecyclerView.adapter = imageAdapter
        }
    }

    override fun movieItem(resultsItem: ResultsItem) {
        clickListener?.detailPage(resultsItem.id.toString().optString())
    }
}

class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val carouselList = ArrayList<ResultsItem>()
    fun bind(itemView: View, resultsItem: ArrayList<ResultsItem>) {
        carouselList.clear()
        carouselList.addAll(resultsItem.take(10))
        itemView.carouselView.pageCount = carouselList.size
        itemView.carouselView.setImageListener(imageListener)
    }

    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

    private val imageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            imageView?.let {
                val url = carouselList[position].backdropPath.optString()
                    .ifEmpty { carouselList[position].posterPath }
                Glide.with(itemView.context)
                    .load(url.optString().toPosterUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(withCrossFade(factory))
                    .placeholder(R.drawable.poster_bg)
                    .error(R.drawable.poster_bg)
                    .into(it)
            }
        }
    }

}

interface ImageClickListener {
    fun movieItem(resultsItem: ResultsItem)
}