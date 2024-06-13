package com.example.metapolitan.data.remote

data class ModelState<T>(
    var isLoading: Boolean = false,
    var response: T? = null,
    val error: String = ""
)