package com.app.videoapplication.page.main.comingSoon

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.videoapplication.api.ApiClient
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.utils.AppRepository
import com.app.videoapplication.utils.DebugUtilis
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ComingSoonViewModel(private val appRepository: AppRepository) : ViewModel() {

    val TAG = "ComingSoonViewModel"
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _resultsItem = MutableLiveData<List<ResultsItem>>()
    val resultsItem : LiveData<List<ResultsItem>> =_resultsItem

    private var totalPages=0
    private var currentPage=1
    private var totalResults=0
    private var reachLevel=false

    fun start(){
        getComingSoon()
    }

    private fun getComingSoon(){
        uiScope.launch {
            appRepository.getUpComing(currentPage)?.let {
                _resultsItem.postValue(it)

                reachLevel = true
            }
        }
    }

    fun nextPage(){
        if(reachLevel){
            reachLevel=false
            currentPage++
            getComingSoon()
        }
    }
}