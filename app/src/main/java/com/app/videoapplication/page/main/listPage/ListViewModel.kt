package com.app.videoapplication.page.main.listPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.videoapplication.Utils.Constants
import com.app.videoapplication.api.ApiClient
import com.app.videoapplication.model.FeedItem
import com.app.videoapplication.model.ResultsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _resultsItem = MutableLiveData<List<ResultsItem>>()
    val resultsItem : LiveData<List<ResultsItem>> =_resultsItem
    private var totalPages=0
    private var currentPage=1
    private var totalResults=0
    private var reachLevel=false
   // private var lastResultItem : ArrayList<ResultsItem> = ArrayList()

    private var title=""

    fun start(title: String){
        this.title=title
        loadData()
    }

    private fun loadData(){
        when(title){
            Constants.POPULAR -> getPopularMovies()
            Constants.TOP_RATED -> getTopRated()
        }
    }

    private fun getTopRated(){
        uiScope.launch {
            val response = ApiClient.callClient.fetchTopRatedMovies(currentPage)
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty()) {
                        _resultsItem.postValue(it)
                        reachLevel = true
                    }
                }
            }
        }
    }

    private fun getPopularMovies(){
        uiScope.launch {
            val response = ApiClient.callClient.fetchPopularMovies(currentPage)
            if(response.isSuccessful){
                response.body()?.results?.let {
                    if(it.isNotEmpty()) {
                        _resultsItem.postValue(it)
                        reachLevel = true
                    }
                }
            }
        }
    }



    fun nextPage(){
        if(reachLevel){
            reachLevel=false
            currentPage++
           loadData()
        }
    }


}