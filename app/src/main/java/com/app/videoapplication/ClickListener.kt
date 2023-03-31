package com.app.videoapplication

interface NextPageListener {
    fun onNextPageCall(position: Int)
}

interface ClickViewAllListener{
    fun viewAll(position: Int,name: String)
}