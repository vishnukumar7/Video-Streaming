package com.app.videoapplication.api

import com.app.videoapplication.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("authentication/token/new")
    suspend fun getToken() : Response<TokenResponse>

    //TV api
    @GET("tv/popular")
    suspend fun getTvPopular() : Response<TvShowResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTv(@Query("language") language : String="en-US",@Query("page")page : Int=1) : Response<TvShowResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTv(@Query("language") language : String="en-US",@Query("page")page : Int =1) : Response<TvShowResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(@Query("language") language : String="en-US",@Query("page")page : Int =1) : Response<TvShowResponse>

    @GET("trending/tv/day")
    suspend fun getTrendingTvByDay(@Query("language") language : String="en-US") : Response<TvShowResponse>

    @GET("trending/tv/week")
    suspend fun getTrendingTvByWeek(@Query("language") language : String="en-US") : Response<TvShowResponse>

    //Movies API
    @GET("movie/popular")
    suspend fun fetchPopularMovies(@Query("page") page: Int=1): Response<TrendingResponse>

    @GET("movie/latest")
    suspend fun fetchLatestMovies(@Query("page") page: Int=1) : Response<TrendingResponse>

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(@Query("page") page: Int=1) : Response<TrendingResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(@Query("language") language : String="en-US",@Query("page")page : Int =1) : Response<TrendingResponse>
    @GET("movie/{movie_id}/lists")
    suspend fun getMovie(@Path("movie_id") id : Int) : Response<MovieViewResponse>

    @GET("movie/upcoming")
    suspend fun getUpComing(@Query("page") page: Int) : Response<ComingSoonResponse>

    @GET("trending/movie/day")
    suspend fun getTrendingMovieByDay(@Query("language") language : String="en-US") : Response<MovieViewResponse>

    @GET("trending/movie/week")
    suspend fun getTrendingMovieByWeek(@Query("language") language : String="en-US") : Response<MovieViewResponse>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(@Path("media_type") mediaType: String,@Path("time_window")timeWindow: String) : Response<TrendingResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieId : String,@Query("language") language : String="en-US") : Response<MovieDetailResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailCall(@Path("movie_id")movieId : String,@Query("language") language : String="en-US") : Call<MovieDetailResponse>

    @GET("genre/movie/list")
    suspend fun getMovieList() : Response<MovieListGenreResponse>

    @GET("genre/tv/list")
    suspend fun getTvList() : Response<MovieListGenreResponse>


}