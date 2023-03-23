package com.app.videoapplication.page.main.Feeds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
            val response = ApiClient.callClient.fetchPopularMovies(1)
            if(response.isSuccessful){
                feedList.clear()
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                    feedList.add(FeedItem(title = "Popular on Netflix", itemList = it))
                }
                _resultsItem.postValue(feedList)
                getTrending()
            }
        }
    }

    private fun getTrending(){
        uiScope.launch {
            val response = ApiClient.callClient.getTrending("all","day")
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                        feedList.add(FeedItem(title = "Trending Now", itemList = it))
                }
                _resultsItem.postValue(feedList)
            }
            getTopRated()
        }
    }

    private fun getTopRated(){
        uiScope.launch {
            val response = ApiClient.callClient.fetchTopRatedMovies(1)
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                    feedList.add(FeedItem(title = "Top Rated on Netflix", itemList = it))
                }
                _resultsItem.postValue(feedList)
            }
            getMovies()

        }
    }

    private fun getMovies(){
        uiScope.launch {
            val response = ApiClient.callClient.getMovieList()
            if(response.isSuccessful){
                response.body()?.genres?.let {
                    tempDataList.addAll(it)
                    println("//genres : $it")
                    getMovieForSelected()
                }
            }

        }
    }

    private fun getMovieForSelected(){
        uiScope.launch {
            println("// data : ${tempDataList.filter { selectedList.contains(it.name) }.size}")
            tempDataList.filter { selectedList.contains(it.name) }.forEach { genres->
                val response = ApiClient.callClient.getMovie(genres.id)
                if(response.isSuccessful){
                    println("//response genres : ${response.body()?.results}")
                    /* response.body()?.results?.let {
                         if(it.isNotEmpty())
                             feedList.add(FeedItem(title = genres.name, itemList = it))
                     }
                     _resultsItem.postValue(feedList)*/
                }
            }
        }
    }




    private fun getLatestMovies(){
        uiScope.launch {
            val response = ApiClient.callClient.fetchLatestMovies(1)
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty())
                    feedList.add(FeedItem(title = "Latest on Netflix", itemList = it))
                }
                _resultsItem.postValue(feedList)
            }
        }
    }

}