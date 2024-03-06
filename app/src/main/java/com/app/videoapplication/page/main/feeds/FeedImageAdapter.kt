package com.app.videoapplication.page.main.feeds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.utils.AppUtils.getImageUrl
import com.app.videoapplication.utils.AppUtils.optString
import com.app.videoapplication.NextPageListener
import com.app.videoapplication.R
import com.app.videoapplication.model.ResultsItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_feed.view.*

const val SOME_ITEM=0
const val ALL_ITEM=1
const val List_ITEM_VIEW=2
const val LOADING_VIEW=3
const val LIST_SOME_ITEM_VIEW=4

class FeedImageAdapter(var context: Context,var listList: ArrayList<ResultsItem> = ArrayList(),var lastPage: Boolean=false,var type : Int = SOME_ITEM,var nextPageListener: NextPageListener? =null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LOADING_VIEW -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_view,parent,false))
            LIST_SOME_ITEM_VIEW -> FeedImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_feed,parent,false))
            else -> FeedImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_view_all,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(type== SOME_ITEM){
            return LIST_SOME_ITEM_VIEW
        }else{
            if(!lastPage)
                return List_ITEM_VIEW
            else if(listList.size==position){
                return LOADING_VIEW
            }
        }
        return List_ITEM_VIEW
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FeedImageViewHolder -> {
                holder.bind(holder.itemView,listList[position])
            }

            is LoadingViewHolder -> {
                nextPageListener?.onNextPageCall(position)
            }
        }
    }

    override fun getItemCount(): Int {
        if(type== SOME_ITEM){
            return listList.size
        }else{
            if(!lastPage)
                return listList.size
            return listList.size+1
        }
    }

}

class FeedImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(itemView: View,resultsItem: ResultsItem){
        Glide.with(itemView.context).load(resultsItem.posterPath.optString().getImageUrl()).into(itemView.feedImageView)
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(){

    }
}



