package com.example.metapolitan.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

abstract class RequestBuilder {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Resource<T> =
        try {
            val response = call()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(message = response.errorBody()?.stringSuspending(), status = "")
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message!!, status = "")
        }
}
suspend fun ResponseBody.stringSuspending() = withContext(Dispatchers.IO) { string() }
