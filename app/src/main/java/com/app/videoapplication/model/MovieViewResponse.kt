package com.app.videoapplication.model

import com.google.gson.annotations.SerializedName

data class MovieViewResponse(

	@field:SerializedName("id")
	val id: Int=-1,

	@field:SerializedName("page")
	val page: Int=1,

	@field:SerializedName("total_pages")
	val totalPages: Int=0,

	@field:SerializedName("results")
	val results: List<MovieResultsItem> = ArrayList(),

	@field:SerializedName("total_results")
	val totalResults: Int=0
)

data class MovieResultsItem(

	@field:SerializedName("item_count")
	val itemCount: Int=0,

	@field:SerializedName("list_type")
	val listType: String="",

	@field:SerializedName("name")
	val name: String="",

	@field:SerializedName("description")
	val description: String="",

	@field:SerializedName("favorite_count")
	val favoriteCount: Int=0,

	@field:SerializedName("id")
	val id: Int=-1,

	@field:SerializedName("iso_639_1")
	val iso6391: String="",

	@field:SerializedName("poster_path")
	val posterPath: String=""
)
