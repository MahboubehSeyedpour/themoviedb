package com.example.metapolitan.data.local

data class Movie(
    val id: String,
    val title: String,
    val year: String,
    val description: String,
    val rating: Float,  // Rating out of 5
    val imageUrl: String
)