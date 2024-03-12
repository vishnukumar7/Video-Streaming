package com.app.videoapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import com.app.videoapplication.BuildConfig

object AppUtils {

   /* fun String.getImageUrl(imageSize: ImageSize = ImageSize.NORMAL) : String {
        return "${BuildConfig.IMAGE_URL}${imageSize.value}$this"
    }*/

    fun String.toPosterUrl() = "https://image.tmdb.org/t/p/w342$this"

    fun String.toBackdropUrl() = "https://image.tmdb.org/t/p/original$this"

    fun String.toProfilePhotoUrl() = "https://image.tmdb.org/t/p/w185$this"

    fun String.toOriginalUrl() = "https://image.tmdb.org/t/p/original$this"

    fun String.toImdbProfileUrl() = "https://www.imdb.com/name/$this"

    fun networkCheck(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun String?.optString(fallback : String="") : String{
        if(this==null)
            return fallback
        else if(this == "null")
            return fallback
        return this
    }

}

enum class ImageSize(val value: String){
    NORMAL("w500"),
    ORIGINAL("original"),
}