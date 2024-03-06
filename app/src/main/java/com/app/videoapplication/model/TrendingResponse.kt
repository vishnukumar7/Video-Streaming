package com.app.videoapplication.model

import com.squareup.moshi.Json

data class TrendingResponse(

	@Json(name = "page")
	val page: Int? = 0,

	@Json(name = "total_pages")
	val totalPages: Int? = 0,

	@Json(name = "results")
	val results: List<ResultsItem> = ArrayList(),

	@Json(name = "total_results")
	val totalResults: Int? = 0
)

data class ResultsItem(

	@Json(name = "overview")
	val overview: String? = "",

	@Json(name = "original_language")
	val originalLanguage: String? = "",

	@Json(name = "original_title")
	val originalTitle: String? = "",

	@Json(name = "video")
	val video: Boolean? =  false,

	@Json(name = "title")
	val title: String? = "",

	@Json(name = "genre_ids")
	val genreIds: List<Int> = ArrayList(),

	@Json(name = "poster_path")
	val posterPath: String? = "",

	@Json(name = "backdrop_path")
	val backdropPath: String? = "",

	@Json(name = "release_date")
	val releaseDate: String? = "",

	@Json(name = "vote_average")
	val voteAverage: Double? = 0.0,

	@Json(name = "popularity")
	val popularity: Double? = 0.0,

	@Json(name = "id")
	val id: Int? = -1,

	@Json(name = "adult")
	val adult: Boolean? = false,

	@Json(name = "vote_count")
	val voteCount: Int? = 0,

	@Json(name = "first_air_date")
	val firstAirDate: String? = "",

	@Json(name = "origin_country")
	val originCountry: List<String> = ArrayList(),

	@Json(name = "original_name")
	val originalName: String? = "",

	@Json(name = "name")
	val name: String? = ""
)


data class FeedItem(
	var title: String,
	var list : Int= 0,
	var itemList : List<Any>,
	var viewAll : Boolean = true
)
