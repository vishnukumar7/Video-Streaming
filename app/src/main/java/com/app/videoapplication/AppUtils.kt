package com.app.videoapplication

object AppUtils {
    val TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmOTA3MTEwOWRiMzNhYmRkOThkNTJlOGExY2M3M2IzZSIsInN1YiI6IjY0MWFiZWYyYTNlNGJhMDA3YjIyNzEyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.NKVnhp7zNop7hScFfZjivfzrVy7IVWIojcpJB46F0n0"

    fun String.getImageUrl(imageSize: ImageSize=ImageSize.NORMAL) : String {
        return "${BuildConfig.IMAGE_URL}${imageSize.value}$this"
    }

}

enum class ImageSize(val value: String){
    NORMAL("w500"),
    ORIGINAL("original"),
}