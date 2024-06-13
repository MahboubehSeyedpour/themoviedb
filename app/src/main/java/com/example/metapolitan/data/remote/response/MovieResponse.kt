package com.example.metapolitan.data.remote.response

data class MovieResponse(
    val page: Int,
    val movies: List<MovieDto>,
    val totalPages: Int,
    val totalResults: Int
)