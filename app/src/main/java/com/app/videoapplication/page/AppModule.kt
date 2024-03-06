package com.app.videoapplication.page

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }
}