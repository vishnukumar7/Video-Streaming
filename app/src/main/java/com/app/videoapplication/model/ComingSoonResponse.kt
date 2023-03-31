package com.app.videoapplication.model

import com.google.gson.annotations.SerializedName

data class ComingSoonResponse(

	@field:SerializedName("dates")
	val dates: Dates,

	@field:SerializedName("page")
	val page: Int=0,

	@field:SerializedName("total_pages")
	val totalPages: Int=0,

	@field:SerializedName("results")
	val results: List<ResultsItem>,

	@field:SerializedName("total_results")
	val totalResults: Int=0
)

data class Dates(

	@field:SerializedName("maximum")
	val maximum: String,

	@field:SerializedName("minimum")
	val minimum: String
)
