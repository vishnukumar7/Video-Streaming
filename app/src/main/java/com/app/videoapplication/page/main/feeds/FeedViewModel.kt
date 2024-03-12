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
                when (type) {
                    "All" -> {
                        getFeeds()
                    }
                    "Movie" -> {
                        getMovieFeed()
                    }
                    "Tv" -> {
                        getTvFeed()
                    }
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
                feedList.add(FeedItem(title = Constants.HOME_HEADER_POPULAR_MOVIE, itemList = it))
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
                        title = Constants.HOME_HEADER_TOP_RATED_MOVIE,
                        itemList = it,
                        list = 0
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.fetchLatestMovies()?.let {
                feedList.add(FeedItem(title = Constants.HOME_HEADER_LATEST_MOVIE, itemList = it))
                _resultsItem.postValue(feedList)
            }
            repository.getTvPopular()?.let {
                feedList.add(FeedItem(title = Constants.HOME_HEADER_POPULAR_TV, itemList = it, viewAll = false))
                _resultsItem.postValue(feedList)
            }
        }
    }

    fun getMovieFeed() {
        feedList.clear()
        uiSupervisorScope.launch {
            repository.getNowPlayingMovie()?.let {
                feedList.add(FeedItem(title = Constants.HEADER_NOW_PLAYING_MOVIE, itemList = it))
                _resultsItem.postValue(feedList)
            }
            repository.fetchPopularMovies()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HOME_HEADER_POPULAR_MOVIE,
                        itemList = it
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.fetchTopRatedMovies()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HOME_HEADER_TOP_RATED_MOVIE,
                        itemList = it
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.getTrendingMovieByDay()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HEADER_TRENDING_BY_DAY_MOVIE,
                        itemList = it,
                        viewAll = false
                    )
                )
                _resultsItem.postValue(feedList)
            }
            repository.getTrendingMovieByWeek()?.let {
                feedList.add(
                    FeedItem(
                        title = Constants.HEADER_TRENDING_BY_WEEK_MOVIE,
                        itemList = it,
                        viewAll = false
                    )
                )
                _resultsItem.postValue(feedList)
            }
        }
    }

    fun getTvFeed(){
        feedList.clear()
        uiSupervisorScope.launch {
            repository.getAiringTodayTv()?.let {
                feedList.add(FeedItem(title = Constants.HEADER_AIRING_TODAY_TV, itemList = it))
                _resultsItem.postValue(feedList)
            }

            repository.getOnTheAirTv()?.let {
                feedList.add(FeedItem(title = Constants.HEADER_ON_THE_AIR_TV, itemList = it))
                _resultsItem.postValue(feedList)
            }

            repository.getTvPopular()?.let {
                feedList.add(FeedItem(title = Constants.HOME_HEADER_POPULAR_TV, itemList = it))
                _resultsItem.postValue(feedList)
            }

            repository.getTopRatedTv()?.let {
                feedList.add(FeedItem(title = Constants.HOME_HEADER_TOP_RATED_TV, itemList = it))
                _resultsItem.postValue(feedList)
            }

            repository.getTrendingTvByDay()?.let {
                feedList.add(FeedItem(title = Constants.HEADER_TRENDING_BY_DAY_TV, itemList = it, viewAll = false))
                _resultsItem.postValue(feedList)
            }

            repository.getTrendingTvByWeek()?.let {
                feedList.add(FeedItem(title = Constants.HEADER_TRENDING_BY_WEEK_TV, itemList = it, viewAll = false))
                _resultsItem.postValue(feedList)
            }


        }
    }

}