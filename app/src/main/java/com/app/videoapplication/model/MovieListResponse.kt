package com.app.videoapplication.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class MovieListResponse(

	@Json(name="genres")
	val genres: List<GenresItem>
)

data class GenresItem(

	@Json(name="name")
	val name: String,

	@Json(name="id")
	val id: Int
)
