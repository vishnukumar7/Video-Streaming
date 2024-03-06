package com.app.videoapplication.page.main.listPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.videoapplication.utils.Constants
import com.app.videoapplication.api.ApiClient
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.utils.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ListViewModel(private val repository: AppRepository) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
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
            repository.fetchTopRatedMovies(currentPage)?.let {
                _resultsItem.postValue(it)
                reachLevel = true
            }
        }
    }

    private fun getPopularMovies(){
        uiScope.launch {
            repository.fetchPopularMovies(currentPage)?.let {
                _resultsItem.postValue(it)
                reachLevel = true
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