package com.app.videoapplication.model

import com.squareup.moshi.Json

data class TvShowResponse(

	@Json(name="page")
	val page: Int?=0,

	@Json(name="total_pages")
	val totalPages: Int?=0,

	@Json(name="results")
	val results: List<ResultsItem> = ArrayList(),

	@Json(name="total_results")
	val totalResults: Int? =0
)

