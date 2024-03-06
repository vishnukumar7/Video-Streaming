package com.app.videoapplication.page.main

import android.app.Application
import com.app.videoapplication.BuildConfig
import com.app.videoapplication.api.ApiComponent
import com.app.videoapplication.api.DaggerApiComponent
import com.app.videoapplication.model.dao.DatabaseDao
import com.app.videoapplication.page.AppModule
import com.app.videoapplication.utils.AppRepository
import retrofit2.Retrofit
import javax.inject.Inject

class MainApplication : Application() {

    private lateinit var apiComponent: ApiComponent

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var dbDao: DatabaseDao

    override fun onCreate() {
        super.onCreate()
        apiComponent = DaggerApiComponent.builder()
            .apiModule(ApiModule(BuildConfig.BASE_URL, this))
            .appModule(AppModule(this))
            .build()
        apiComponent.inject(this)
        appRepository.fetchGenre()
    }

    fun getNetComponent(): ApiComponent = apiComponent

    val appRepository by lazy { AppRepository(dbDao) }


}