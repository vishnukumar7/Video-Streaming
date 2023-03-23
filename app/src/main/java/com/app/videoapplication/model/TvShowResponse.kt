package com.app.videoapplication.model

import com.squareup.moshi.Json

data class TvShowResponse(

	@Json(name="page")
	val page: Int?=0,

	@Json(name="total_pages")
	val totalPages: Int?=0,

	@Json(name="results")
	val results: List<TvShowResultsItem> = ArrayList(),

	@Json(name="total_results")
	val totalResults: Int? =0
)

data class TvShowResultsItem(

	@Json(name="first_air_date")
	val firstAirDate: String?="",

	@Json(name="overview")
	val overview: String?="",

	@Json(name="original_language")
	val originalLanguage: String?="",

	@Json(name="genre_ids")
	val genreIds: List<Int> = ArrayList(),

	@Json(name="poster_path")
	val posterPath: String?="",

	@Json(name="origin_country")
	val originCountry: List<String> = ArrayList(),

	@Json(name="backdrop_path")
	val backdropPath: String? = "",

	@Json(name="popularity")
	val popularity: Double? = 0.0,

	@Json(name="vote_average")
	val voteAverage: Double? = 0.0,

	@Json(name="original_name")
	val originalName: String?="",

	@Json(name="name")
	val name: String?="",

	@Json(name="id")
	val id: Int=-1,

	@Json(name="vote_count")
	val voteCount: Int=0
)
