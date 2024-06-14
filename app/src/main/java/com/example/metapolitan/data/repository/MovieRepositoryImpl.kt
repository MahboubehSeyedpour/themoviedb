package com.example.metapolitan.data.repository

import android.util.Log
import com.example.metapolitan.data.remote.ApiService
import com.example.metapolitan.data.remote.RequestBuilder
import com.example.metapolitan.data.remote.Resource
import com.example.metapolitan.data.remote.response.MovieResponse
import com.example.metapolitan.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository, RequestBuilder() {
    override suspend fun getPopularMovies(page: Int): Resource<MovieResponse> {
        // Make network call or database query
        Log.d("MovieRepository", "Fetching popular movies page: $page")
        val response: Resource<MovieResponse> =
            apiRequest { apiService.getPopularMovies(language = "en-US", page) }
        Log.d("MovieRepository", "popular movies: ${response.data?.results}")
        return response
    }

    override suspend fun getTopRatedMovies(page: Int): Resource<MovieResponse> {
        // Make network call or database query
        Log.d("MovieRepository", "Fetching top rated page: $page")
        val response: Resource<MovieResponse> =
            apiRequest { apiService.getTopRatedMovies(language = "en-US", page) }
        Log.d("MovieRepository", "top rated movies: ${response.data?.results}")
        return response
    }

    override suspend fun getUpComingMovies(page: Int): Resource<MovieResponse> {
        // Make network call or database query
        Log.d("MovieRepository", "Fetching upcoming page: $page")
        val response: Resource<MovieResponse> =
            apiRequest { apiService.getUpcomingMovies(language = "en-US", page) }
        Log.d("MovieRepository", "upcoming movies: ${response.data?.results}")
        return response
    }

}
