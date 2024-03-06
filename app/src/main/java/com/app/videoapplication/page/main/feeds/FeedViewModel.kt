package com.app.videoapplication.page.main.feeds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.model.GenresItem
import com.app.videoapplication.utils.AppRepository
import com.app.videoapplication.utils.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FeedViewModel(private val repository: AppRepository) : ViewModel() {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("FeedViewModel", "coroutineExceptionHandler: ${throwable.message} ")
            if(throwable.message?.contains("reset")==true){
                if(type=="All"){
                    getFeeds()
                }else if(type=="Movie"){
                    getMovieFeed()
                }
            }

        }
    private val uiSupervisorScope =
        CoroutineScope(Dispatchers.Main + SupervisorJob() + coroutineExceptionHandler)
    private val _resultsItem = MutableLiveData<List<FeedItem>>()
    val resultsItem: LiveData<List<FeedItem>> = _resultsItem
    private val feedList = ArrayList<FeedItem>()
    var type="All"
    private var tempDataList = ArrayList<GenresItem>()
    private val selectedList = arrayListOf("Action", "Adventure", "Fantasy")

    fun getFeeds() {
        feedList.clear()
        uiSupervisorScope.launch {
            repository.getTrending()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HEADER_TRENDING_BY_DAY_MOVIE,
                        itemList = it,
                        viewAll = false
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.fetchPopularMovies()?.let {
                feedList.add(FeedItem(title = Constants.POPULAR, itemList = it))
                _resultsItem.postValue(feedList)
            }

            repository.getTrending("all", "week")?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HEADER_TRENDING_BY_WEEK_MOVIE,
                        itemList = it, viewAll = false
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.fetchTopRatedMovies()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HOME_HEADER_POPULAR_MOVIE,
                        itemList = it,
                        list = 0
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.fetchLatestMovies()?.let {
                feedList.add(FeedItem(title = Constants.LATEST, itemList = it))
                _resultsItem.postValue(feedList)
            }
            repository.getTvPopular()?.let {
                feedList.add(FeedItem(title = Constants.HOME_HEADER_POPULAR_TV, itemList = it))
                _resultsItem.postValue(feedList)
            }
        }
    }

    fun getMovieFeed() {
        feedList.clear()
        uiSupervisorScope.launch {
            repository.fetchPopularMovies()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HOME_HEADER_POPULAR_MOVIE,
                        itemList = it,
                        list = 0
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.fetchTopRatedMovies()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HOME_HEADER_POPULAR_MOVIE,
                        itemList = it,
                        list = 0
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.getTrendingMovieByDay()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HEADER_TRENDING_BY_DAY_MOVIE,
                        itemList = it,
                        list = 0, viewAll = false
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.getTrendingMovieByWeek()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HEADER_TRENDING_BY_WEEK_MOVIE,
                        itemList = it,
                        list = 0,
                        viewAll = false
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.getUpComing()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HEADER_UP_COMING_MOVIE,
                        itemList = it,
                        list = 0
                    )
                )
                _resultsItem.postValue(feedList)
            }
        }
    }

}