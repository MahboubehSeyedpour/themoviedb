package com.example.metapolitan.domain.repository

import com.example.metapolitan.data.remote.Resource
import com.example.metapolitan.data.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Resource<MovieResponse>
    suspend fun getTopRatedMovies(page: Int): Resource<MovieResponse>
    suspend fun getUpComingMovies(page: Int): Resource<MovieResponse>
    suspend fun getSearchResult(query: String, page: Int): Resource<MovieResponse>
}