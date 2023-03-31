package com.app.videoapplication.page.main.Feeds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.videoapplication.Utils.Constants
import com.app.videoapplication.api.ApiClient
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.model.GenresItem
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _resultsItem = MutableLiveData<List<FeedItem>>()
    val resultsItem : LiveData<List<FeedItem>> =_resultsItem
    private val feedList= ArrayList<FeedItem>()
    private var tempDataList = ArrayList<GenresItem>()
    private val selectedList= arrayListOf("Action","Adventure","Fantasy")

    fun getFeeds(){
        uiScope.launch {
            val response = ApiClient.callClient.getTrending("all","day")
            if(response.isSuccessful){
                response.body()?.results?.let {
                    feedList.clear()
                    if(it.isNotEmpty()){
                        feedList.add(FeedItem(title = Constants.TRENDING_DAY, itemList = it))
                    }
                }
                _resultsItem.postValue(feedList)
            }
            getPopularMovies()
        }
    }

    private fun getTrendingWeek(){
        uiScope.launch {
            val response = ApiClient.callClient.getTrending("all","week")
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                        feedList.add(FeedItem(title =Constants.TRENDING_WEEK, itemList = it))
                }

            }
            getTopRated()
        }
    }

    private fun getPopularMovies(){
        uiScope.launch {
            val response = ApiClient.callClient.fetchPopularMovies(1)
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                        feedList.add(FeedItem(title = Constants.POPULAR, itemList = it))
                }
                _resultsItem.postValue(feedList)
                getTrendingWeek()
            }
        }
    }

    private fun getTopRated(){
        uiScope.launch {
            val response = ApiClient.callClient.fetchTopRatedMovies(1)
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                    feedList.add(FeedItem(title = Constants.TOP_RATED, itemList = it))
                }
                _resultsItem.postValue(feedList)
            }
            getLatestMovies()
        }
    }


    private fun getLatestMovies(){
        uiScope.launch {
            val response = ApiClient.callClient.fetchLatestMovies(1)
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                    feedList.add(FeedItem(title = Constants.LATEST, itemList = it))
                }
                _resultsItem.postValue(feedList)
            }
        }
        getTvPopular()
    }

    private fun getTvPopular(){
        uiScope.launch {
            val response = ApiClient.callClient.getTvPopular()
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                        feedList.add(FeedItem(title = Constants.POPULAR_TV, itemList = it))
                }
                _resultsItem.postValue(feedList)
            }
        }
    }

}