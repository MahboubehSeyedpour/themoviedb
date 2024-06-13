package com.example.metapolitan.data.repository

import com.example.metapolitan.data.remote.ApiService
import com.example.metapolitan.data.remote.RequestBuilder
import com.example.metapolitan.data.remote.Resource
import com.example.metapolitan.data.remote.response.MovieResponse
import com.example.metapolitan.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository, RequestBuilder() {
    override fun getPopularMovies(): Flow<Resource<MovieResponse>> =
        requestBuilder { apiService.getPopularMovies("en-US", 1) }
}
