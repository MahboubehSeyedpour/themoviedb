package com.example.metapolitan.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class RequestBuilder {

    fun <T : Any> requestBuilder(call: suspend () -> Response<T>): Flow<Resource<T>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = call()
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    response.errorBody()?.let { errorBody ->

                        val error = errorBody.stringSuspending()
                        emit(Resource.Error(message = error, status = ""))
                    }
                }
            } catch (ex: NoInternetException) {
                emit(
                    Resource.Error(
                        message = ex.message!!,
                        status = ""
                    )
                )
            } catch (ex: HttpException) {
                emit(
                    Resource.Error(
                        message = ex.localizedMessage ?: "An unexpected error occurred",
                        status = ""
                    )
                )
            } catch (ex: IOException) {
                emit(
                    Resource.Error(
                        message = "Couldn't reach server. Check your internet connection.",
                        status = ""
                    )
                )
            }!!
        }
    }
}

suspend fun ResponseBody.stringSuspending() = withContext(Dispatchers.IO) { string() }
