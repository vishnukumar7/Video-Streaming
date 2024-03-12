package com.app.videoapplication.page.main.comingSoon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.videoapplication.utils.AppUtils.optString
import com.app.videoapplication.utils.ImageSize
import com.app.videoapplication.NextPageListener
import com.app.videoapplication.R
import com.app.videoapplication.model.GenresItem
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.page.MainPageActivity
import com.app.videoapplication.page.main.feeds.LOADING_VIEW
import com.app.videoapplication.page.main.feeds.List_ITEM_VIEW
import com.app.videoapplication.page.main.feeds.LoadingViewHolder
import com.app.videoapplication.utils.AppUtils.toBackdropUrl
import com.app.videoapplication.utils.AppUtils.toOriginalUrl
import com.app.videoapplication.utils.AppUtils.toPosterUrl
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.coming_soon_view.view.*

class ComingSoonAdapter(var listItem : ArrayList<ResultsItem>, private var nextPageListener: NextPageListener,var activity: MainPageActivity?=null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                holder.bind(holder.itemView,listItem[position],activity?.appRepository?.genreList)
            }


        }
    }

    override fun getItemCount(): Int {
        return listItem.size+1
    }
}

class ComingSoonViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    fun bind(itemView: View, resultsItem: ResultsItem, genreList: List<GenresItem>?=null){
        Glide.with(itemView.context).load(resultsItem.backdropPath.optString().toBackdropUrl()).into(itemView.comingImageView)
        itemView.name.text=resultsItem.title
        itemView.rating.text=String.format("%.1f/10",resultsItem.voteAverage?.toFloat())

        var genres = ""
        resultsItem.genreIds.forEach {genresId->
            val value =genreList?.first { it.id==genresId }?.name
            if(genres.isBlank()){
                genres=value ?: ""
            }else{
                genres+=value?.let { ", $it" } ?: kotlin.run { "" }
            }
        }
        itemView.releaseDate.text=resultsItem.releaseDate
        itemView.genre.text=genres
        itemView.overview.text=resultsItem.overview
    }
}