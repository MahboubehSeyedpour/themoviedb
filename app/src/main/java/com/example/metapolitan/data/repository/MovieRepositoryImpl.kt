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
        Log.d("MovieRepository", "Fetching page: $page")
        val response: Resource<MovieResponse> = apiRequest { apiService.getPopularMovies(language = "en-US", page) }
        Log.d("MovieRepository", "Movies: ${response.data?.results}")
        return response
    }

}
