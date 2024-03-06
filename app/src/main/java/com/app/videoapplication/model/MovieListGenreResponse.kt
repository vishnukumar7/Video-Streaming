package com.app.videoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class MovieListGenreResponse(

	@Json(name="genres")
	val genres: List<GenresItem>
)

@Entity(tableName = "genre_movie_table")
data class GenresItem(

	@ColumnInfo
	@Json(name="name")
	val name: String,

	@ColumnInfo
	@PrimaryKey
	@Json(name="id")
	val id: Int
)
