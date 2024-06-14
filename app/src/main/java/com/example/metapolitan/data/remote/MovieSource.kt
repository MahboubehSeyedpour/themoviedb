package com.example.metapolitan.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.metapolitan.data.remote.response.Movie
import com.example.metapolitan.data.remote.response.MovieResponse
import com.example.metapolitan.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            Log.d("MoviePagingSource", "Loading page: $nextPage")
            val list = movieRepository.getPopularMovies(page = nextPage)
            val totalPages = list.data?.totalPages ?: 0
            Log.d("MoviePagingSource", "Loaded movies: ${list.data?.results?.size}, Total pages: $totalPages")
            LoadResult.Page(
                data = list.data?.results ?: emptyList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < list.data?.totalPages!!) nextPage + 1 else null
            )
        } catch (exception: IOException) {
            Log.e("MoviePagingSource", "IOException: ${exception.localizedMessage}")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("MoviePagingSource", "HttpException: ${exception.localizedMessage}")
            LoadResult.Error(exception)
        }
    }
}