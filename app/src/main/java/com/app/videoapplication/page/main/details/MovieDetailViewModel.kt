package com.app.videoapplication.page.main.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.app.videoapplication.model.MovieDetailResponse
import com.app.videoapplication.utils.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: AppRepository) : ViewModel() {

    val TAG="MovieDetailViewModel"
    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val _resultsItem = MutableLiveData<MovieDetailResponse>()
    val resultsItem : LiveData<MovieDetailResponse> =_resultsItem

    val movieDetailResponseObserver = Observer<MovieDetailResponse>{
        _resultsItem.postValue(it)
    }
    fun start(movieId: String){
        uiScope.launch {
            Log.e(TAG, "start: launch", )
            /*repository.getMovieDetail(movieId)?.let {
                Log.e(TAG, "start: ${Gson().toJson(it)}", )
                _resultsItem.postValue(it)
            }*/
            repository.movieResponse.observeForever(movieDetailResponseObserver)
            repository.getMovieDetailCall(movieId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.movieResponse.removeObserver(movieDetailResponseObserver)
    }


}