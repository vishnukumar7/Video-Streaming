package com.app.videoapplication.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.videoapplication.model.GenresItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genresItem: GenresItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genresItemList: List<GenresItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(genresItem: GenresItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(genresItemList: List<GenresItem>)

    @Query("select * from genre_movie_table")
    fun getGenreAllAsFlow() : Flow<MutableList<GenresItem>>

    @Query("select * from genre_movie_table")
    fun getGenreAll() : List<GenresItem>

    @Query("select name from genre_movie_table where id=:id limit 1")
    fun getNameFromId(id : Int) :String

}