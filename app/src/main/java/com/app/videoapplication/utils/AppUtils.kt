package com.app.videoapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import com.app.videoapplication.BuildConfig

object AppUtils {

    fun String.getImageUrl(imageSize: ImageSize = ImageSize.NORMAL) : String {
        return "${BuildConfig.IMAGE_URL}${imageSize.value}$this"
    }

    fun networkCheck(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun String?.optString() : String{
        if(this==null)
            return ""
        return this
    }

}

enum class ImageSize(val value: String){
    NORMAL("w500"),
    ORIGINAL("original"),
}