package com.app.videoapplication.api

import com.app.videoapplication.page.AppModule
import com.app.videoapplication.page.main.ApiModule
import com.app.videoapplication.page.main.MainApplication
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class,ApiModule::class])
interface ApiComponent {

    fun inject(application: MainApplication)


}