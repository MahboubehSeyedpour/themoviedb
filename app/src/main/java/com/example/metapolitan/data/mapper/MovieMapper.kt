package com.example.metapolitan.data.mapper

import com.example.metapolitan.data.local.Movie
import com.example.metapolitan.data.remote.response.MovieDto

fun MovieDto.toLocalMovie(): Movie {
    return Movie(
        id = this.id.toString(),
        year =this.releaseDate,
        rating = this.voteAverage.toFloat(),
        description = this.overview,
        title = this.title,
        imageUrl = this.posterPath
    )
}