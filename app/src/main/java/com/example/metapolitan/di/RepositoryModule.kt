package com.example.metapolitan.di

import com.example.metapolitan.data.repository.MovieRepositoryImpl
import com.example.metapolitan.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}