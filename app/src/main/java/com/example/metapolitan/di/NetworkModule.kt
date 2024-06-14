package com.example.metapolitan.di

import com.example.metapolitan.BuildConfig
import com.example.metapolitan.data.remote.ApiService
import com.example.metapolitan.data.remote.RequestBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideDispatcher(): Dispatcher {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        return dispatcher
    }

    @Singleton
    @Provides
    fun provideLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        logging: HttpLoggingInterceptor,
        dispatcher: Dispatcher
    ) =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(
                Interceptor {chain ->
                    val requestBuilder = chain.request().newBuilder()

                    requestBuilder.apply {
                        addHeader("Accept", "application/json")
                        addHeader("Content-Type", "application/json;charset=utf-8")
                        addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    }

                    chain.proceed(requestBuilder.build())
                }
            )
            .connectTimeout(30, TimeUnit.SECONDS) // connect timeout
            .writeTimeout(30, TimeUnit.SECONDS) // write timeout
            .readTimeout(30, TimeUnit.SECONDS) // read timeout
            .retryOnConnectionFailure(true)
            .dispatcher(dispatcher)
            .build()

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    fun provideRequestBuilder(): RequestBuilder {
        return object : RequestBuilder() {}
    }
}