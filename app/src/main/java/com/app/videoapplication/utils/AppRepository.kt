package com.app.videoapplication.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.videoapplication.api.ApiClient
import com.app.videoapplication.model.MovieDetailResponse
import com.app.videoapplication.model.MovieResultsItem
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.model.dao.DatabaseDao
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(private val databaseDao: DatabaseDao){

    val TAG="AppRepository"
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, "coroutineExceptionHandler: ${throwable.message} and")
            throwable.printStackTrace()

        }
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + coroutineExceptionHandler)

    val genreListFlow by lazy { databaseDao.getGenreAllAsFlow() }

    val genreList by lazy { databaseDao.getGenreAll() }

    private val _movieResponse = MutableLiveData<MovieDetailResponse>()
    val movieResponse : LiveData<MovieDetailResponse> =_movieResponse

    suspend fun getTrending(mediaType : String ="all",timeWindow : String = "day") : List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getTrending(mediaType,timeWindow)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun fetchPopularMovies(pageNumber : Int =1 ) : List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.fetchPopularMovies(pageNumber)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun fetchTopRatedMovies(pageNumber : Int =1 ) : List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.fetchTopRatedMovies(pageNumber)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getAiringTodayTv(pageNumber : Int =1 ) : List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getAiringTodayTv(page = pageNumber)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getOnTheAirTv(pageNumber : Int =1 ) : List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getOnTheAirTv(page = pageNumber)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getNowPlayingMovie(pageNumber : Int =1 ) : List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getNowPlayingMovie(page = pageNumber)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getTopRatedTv(pageNumber : Int =1 ) : List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getTopRatedTv(page = pageNumber)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getTrendingMovieByDay(): List<MovieResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getTrendingMovieByDay()
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getTrendingMovieByWeek(): List<MovieResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getTrendingMovieByWeek()
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getUpComing(pageNumber: Int=1): List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getUpComing(pageNumber)
        if(response.isSuccessful){
            DebugUtilis.e(TAG, "getComingSoon: ${Gson().toJson(response.body())}", )
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getTvPopular(): List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getTvPopular()
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getTrendingTvByDay(): List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getTrendingTvByDay()
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getTrendingTvByWeek(): List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.getTrendingTvByWeek()
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun fetchLatestMovies(pageNumber: Int=1): List<ResultsItem>? = uiScope.async {
        val response = ApiClient.callClient.fetchLatestMovies(pageNumber)
        if(response.isSuccessful){
            response.body()?.results?.ifEmpty { null }
        }else{
            null
        }
    }.await()

    suspend fun getMovieDetail(movieId : String) : MovieDetailResponse? = uiScope.async {
        val response = ApiClient.callClient.getMovieDetail(movieId)
        Log.e(TAG, "getMovieDetail: ", )
        if(response.isSuccessful){
            Log.e(TAG, "getMovieDetail: success : ${Gson().toJson(response.body())}", )
            response.body()
        }else {
            Log.e(TAG, "getMovieDetail: failure ${response.message()}", )
            null
        }
    }.await()

    fun getMovieDetailCall(movieId: String) {
        uiScope.launch {
            val responseCall = ApiClient.callClient.getMovieDetailCall(movieId)
            responseCall.enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if(response.isSuccessful && response.body()!=null){
                        _movieResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}", )
                }

            })
        }
    }

    fun fetchGenre() {
        uiScope.launch {
            async {
                val response = ApiClient.callClient.getMovieList()
                if(response.isSuccessful && response.body()!=null){
                    databaseDao.insert(response.body()!!.genres)
                }
            }.await()
            async {
                val response = ApiClient.callClient.getTvList()
                if(response.isSuccessful && response.body()!=null){
                    databaseDao.insert(response.body()!!.genres)
                }
            }.await()
        }
    }


    fun getNameFromId(id : Int) : String = databaseDao.getNameFromId(id)
}