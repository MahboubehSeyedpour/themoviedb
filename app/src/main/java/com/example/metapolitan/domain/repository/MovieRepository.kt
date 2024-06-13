package com.example.metapolitan.domain.repository

import com.example.metapolitan.data.remote.Resource
import com.example.metapolitan.data.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<Resource<MovieResponse>>
}