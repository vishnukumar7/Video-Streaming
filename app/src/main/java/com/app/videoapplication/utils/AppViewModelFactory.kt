package com.app.videoapplication.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.videoapplication.page.main.comingSoon.ComingSoonViewModel
import com.app.videoapplication.page.main.feeds.FeedViewModel
import com.app.videoapplication.page.main.listPage.ListViewModel

class AppViewModelFactory(private val appRepository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)){
            return FeedViewModel(appRepository) as T
        }
        else if(modelClass.isAssignableFrom(ListViewModel::class.java)){
            return ListViewModel(appRepository) as T
        }else if(modelClass.isAssignableFrom(ComingSoonViewModel::class.java)){
            return ComingSoonViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}