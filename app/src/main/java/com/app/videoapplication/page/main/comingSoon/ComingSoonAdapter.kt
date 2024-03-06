package com.app.videoapplication.page.main.comingSoon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.utils.AppUtils.getImageUrl
import com.app.videoapplication.utils.AppUtils.optString
import com.app.videoapplication.utils.ImageSize
import com.app.videoapplication.NextPageListener
import com.app.videoapplication.R
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.page.main.feeds.LOADING_VIEW
import com.app.videoapplication.page.main.feeds.List_ITEM_VIEW
import com.app.videoapplication.page.main.feeds.LoadingViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.coming_soon_view.view.*

class ComingSoonAdapter(var listItem : ArrayList<ResultsItem>,var nextPageListener: NextPageListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            List_ITEM_VIEW -> ComingSoonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.coming_soon_view,parent,false))
            else -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_view,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(listItem.size==position)
            return LOADING_VIEW
        return List_ITEM_VIEW
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LoadingViewHolder->{
                nextPageListener.onNextPageCall(position)
            }
            is ComingSoonViewHolder->{
                holder.bind(holder.itemView,listItem[position])
            }


        }
    }

    override fun getItemCount(): Int {
        return listItem.size+1
    }
}

class ComingSoonViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    fun bind(itemView: View,resultsItem: ResultsItem){
        Glide.with(itemView.context).load(resultsItem.backdropPath.optString().getImageUrl(ImageSize.ORIGINAL)).into(itemView.comingImageView)
    }
}