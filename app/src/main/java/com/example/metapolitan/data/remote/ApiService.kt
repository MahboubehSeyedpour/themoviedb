package com.example.metapolitan.data.remote

import com.example.metapolitan.data.remote.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("/3/search/movie")
    suspend fun getSearchResult(
        @Query("query") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>


}