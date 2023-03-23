package com.app.videoapplication.api

import com.app.videoapplication.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {


    private fun getClient() : OkHttpClient{
        val builder=OkHttpClient.Builder()
        builder.addInterceptor {
            var request=it.request()
            val url=request.url.newBuilder().addQueryParameter("api_key",BuildConfig.API_KEY).build()
            request=request.newBuilder().url(url).build()
            it.proceed(request)
        }
        return builder.build()
    }

    private val moshi: Moshi = Moshi.Builder()
        /*.add(
            PolymorphicJsonAdapterFactory.of(Media::class.java, "media_type")
                .withSubtype(Media.Movie::class.java, "movie")
                .withSubtype(Media.Tv::class.java, "tv")
                .withSubtype(Media.Person::class.java, "person")
        )
        .add(
            PolymorphicJsonAdapterFactory.of(FeedItem::class.java, "type")
                .withSubtype(FeedItem.Header::class.java, "header")
                .withSubtype(FeedItem.HorizontalList::class.java, "horizontal_list")
        )*/
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun getServiceClient() : APIInterface{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(APIInterface::class.java)
    }

    val callClient= getServiceClient()
}