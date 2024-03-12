package com.app.videoapplication.model

import com.squareup.moshi.Json

data class MovieDetailResponse(

	@Json(name="original_language")
	val originalLanguage: String = "",

	@Json(name="imdb_id")
	val imdbId: String = "",

	@Json(name="video")
	val video: Boolean = false,

	@Json(name="title")
	val title: String = "",

	@Json(name="backdrop_path")
	val backdropPath: String = "",

	@Json(name="revenue")
	val revenue: Int = -1,

	@Json(name="genres")
	val genres: List<GenresItem> = ArrayList(),

	@Json(name="popularity")
	val popularity: Double = 0.0,

	@Json(name="production_countries")
	val productionCountries: List<ProductionCountriesItem> = ArrayList(),

	@Json(name="id")
	val id: Int = -1,

	@Json(name="vote_count")
	val voteCount: Int = -1,

	@Json(name="budget")
	val budget: Int = -1,

	@Json(name="overview")
	val overview: String = "",

	@Json(name="original_title")
	val originalTitle: String = "",

	@Json(name="runtime")
	val runtime: Int = -1,

	@Json(name="poster_path")
	val posterPath: String = "",

	@Json(name="spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem> = ArrayList(),

	@Json(name="production_companies")
	val productionCompanies: List<ProductionCompaniesItem> = ArrayList(),

	@Json(name="release_date")
	val releaseDate: String = "",

	@Json(name="vote_average")
	val voteAverage: Double = 0.0,

	@Json(name="belongs_to_collection")
	val belongsToCollection: List<BelongsToCollection> = ArrayList(),

	@Json(name="tagline")
	val tagline: String = "",

	@Json(name="adult")
	val adult: Boolean = false,

	@Json(name="homepage")
	val homepage: String = "",

	@Json(name="status")
	val status: String = ""
)

data class SpokenLanguagesItem(

	@Json(name="name")
	val name: String = "",

	@Json(name="iso_639_1")
	val iso6391: String = "",

	@Json(name="english_name")
	val englishName: String = ""
)

data class ProductionCountriesItem(

	@Json(name="iso_3166_1")
	val iso31661: String = "",

	@Json(name="name")
	val name: String = ""
)

data class ProductionCompaniesItem(

	@Json(name="logo_path")
	val logoPath: String = "",

	@Json(name="name")
	val name: String = "",

	@Json(name="id")
	val id: Int = -1,

	@Json(name="origin_country")
	val originCountry: String = ""
)

data class BelongsToCollection(

	@Json(name="poster_path")
	val posterPath: String = "",

	@Json(name="name")
	val name: String = "",

	@Json(name="id")
	val id: Int = -1,

	@Json(name="backdrop_path")
	val backdropPath: String = ""
)
