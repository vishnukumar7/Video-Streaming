package com.app.videoapplication.page.main.feeds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.ClickHandlerListener
import com.app.videoapplication.NextPageListener
import com.app.videoapplication.R
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.utils.AppUtils.optString
import com.app.videoapplication.utils.AppUtils.toPosterUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlinx.android.synthetic.main.list_feed.view.feedImageView

const val SOME_ITEM = 0
const val ALL_ITEM = 1
const val List_ITEM_VIEW = 2
const val LOADING_VIEW = 3
const val LIST_SOME_ITEM_VIEW = 4

class FeedImageAdapter(
    var context: Context,
    var listList: ArrayList<ResultsItem> = ArrayList(),
    var lastPage: Boolean = false,
    var type: Int = SOME_ITEM,
    var nextPageListener: NextPageListener? = null,
    private var imageClickListener: ImageClickListener?=null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADING_VIEW -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.loading_view, parent, false)
            )

            LIST_SOME_ITEM_VIEW -> FeedImageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_feed, parent, false)
            )

            else -> FeedImageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_view_all, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (type == SOME_ITEM) {
            return LIST_SOME_ITEM_VIEW
        } else {
            if (!lastPage)
                return List_ITEM_VIEW
            else if (listList.size == position) {
                return LOADING_VIEW
            }
        }
        return List_ITEM_VIEW
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedImageViewHolder -> {
                holder.bind(holder.itemView, listList[position],imageClickListener)
            }

            is LoadingViewHolder -> {
                nextPageListener?.onNextPageCall(position)
            }
        }
    }

    override fun getItemCount(): Int {
        if (type == SOME_ITEM) {
            return listList.size
        } else {
            if (!lastPage)
                return listList.size
            return listList.size + 1
        }
    }

}

class FeedImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    fun bind(itemView: View, resultsItem: ResultsItem,imageClickListener: ImageClickListener?) {
        Glide.with(itemView.context).load(resultsItem.posterPath.optString().toPosterUrl())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .placeholder(R.drawable.poster_bg)
            .error(R.drawable.poster_bg)
            .into(itemView.feedImageView)
        itemView.feedImageView.setOnClickListener {
            imageClickListener?.movieItem(resultsItem)
        }
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind() {

    }
}



