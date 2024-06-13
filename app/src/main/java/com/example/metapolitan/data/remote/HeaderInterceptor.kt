package com.example.metapolitan.data.remote

import com.example.metapolitan.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.apply {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json;charset=utf-8")
            addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
        }

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}