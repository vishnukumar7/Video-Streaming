@file:Suppress("UNCHECKED_CAST")

package com.app.videoapplication.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.videoapplication.page.main.comingSoon.ComingSoonViewModel
import com.app.videoapplication.page.main.details.MovieDetailViewModel
import com.app.videoapplication.page.main.feeds.FeedViewModel
import com.app.videoapplication.page.main.listPage.ListViewModel

class AppViewModelFactory(private val appRepository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FeedViewModel::class.java) -> {
                FeedViewModel(appRepository) as T
            }

            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(appRepository) as T
            }

            modelClass.isAssignableFrom(ComingSoonViewModel::class.java) -> {
                ComingSoonViewModel(appRepository) as T
            }

            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(appRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}