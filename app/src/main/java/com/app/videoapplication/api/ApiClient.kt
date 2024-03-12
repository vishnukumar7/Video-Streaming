package com.app.videoapplication.api

import com.app.videoapplication.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {


    private fun getClient() : OkHttpClient{
        val builder=OkHttpClient.Builder()
        builder.apply {
            readTimeout(1,TimeUnit.MINUTES)
            writeTimeout(1,TimeUnit.MINUTES)
            connectTimeout(1,TimeUnit.MINUTES)
            addInterceptor(getLogging())
            addInterceptor {
                var request=it.request()
                val url=request.url.newBuilder().addQueryParameter("api_key",BuildConfig.API_KEY).build()
                request=request.newBuilder().url(url).build()
                it.proceed(request)
            }
        }
        return builder.build()
    }

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun getServiceClient() : APIInterface{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(APIInterface::class.java)
    }

    val callClient= getServiceClient()

    private fun getLogging(): HttpLoggingInterceptor {
        try {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }
            return logging
        } catch (e: Exception) {
            throw e
        }
    }
}