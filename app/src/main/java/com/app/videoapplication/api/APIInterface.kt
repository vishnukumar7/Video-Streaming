package com.app.videoapplication.api

import com.app.videoapplication.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("authentication/token/new")
    suspend fun getToken() : Response<TokenResponse>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(@Path("media_type") mediaType: String,@Path("time_window")timeWindow: String) : Response<TrendingResponse>

    @GET("movie/popular")
    suspend fun fetchPopularMovies(@Query("page") page: Int): Response<TrendingResponse>

    @GET("movie/latest")
    suspend fun fetchLatestMovies(@Query("page") page: Int) : Response<TrendingResponse>

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(@Query("page") page: Int) : Response<TrendingResponse>


    @GET("genre/movie/list")
    suspend fun getMovieList() : Response<MovieListResponse>

    @GET("movie/{movie_id}/lists")
    suspend fun getMovie(@Path("movie_id") id : Int) : Response<MovieViewResponse>

    @GET("tv/popular")
    suspend fun getTvPopular() : Response<TvShowResponse>

    @GET("movie/upcoming")
    suspend fun getUpComing(@Query("page") page: Int) : Response<ComingSoonResponse>

}