package com.app.videoapplication.page.main.comingSoon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.videoapplication.model.ResultsItem
import com.app.videoapplication.utils.AppRepository
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
                it.forEach {resultsItem->
                    if(resultsItem.genreIds.isNotEmpty()){
                        resultsItem.genreIds.forEach {
                            //resultsItem.genreIdString.add(appRepository.getNameFromId(it))
                        }
                    }
                }
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