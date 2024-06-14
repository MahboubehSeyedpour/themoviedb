package com.example.metapolitan.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.metapolitan.data.remote.response.Movie
import com.example.metapolitan.data.remote.response.MovieResponse
import com.example.metapolitan.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

abstract class BasePagingSource<T>(
    private val repositoryCall: suspend (Int) -> Resource<MovieResponse>
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            Log.d("MoviePagingSource", "Loading page: $nextPage")
            val response = repositoryCall(nextPage)
            val totalPages = response.data?.totalPages ?: 0
            Log.d("MoviePagingSource", "Loaded movies: ${response.data?.results?.size}, Total pages: $totalPages")
            val data = response.data?.results ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.data?.totalPages!!) nextPage + 1 else null
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

abstract class SearchPagingSource<T>(
    private val repositoryCall: suspend (String, Int) -> Resource<MovieResponse>,
    private val query: String
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
            Log.d("SearchPagingSource", "Loading page: $nextPage with query: $query")
            val response = repositoryCall(query, nextPage)
            val totalPages = response.data?.totalPages ?: 0
            Log.d("SearchPagingSource", "Loaded movies: ${response.data?.results?.size}, Total pages: $totalPages")
            val data = response.data?.results ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < totalPages) nextPage + 1 else null
            )
        } catch (exception: IOException) {
            Log.e("SearchPagingSource", "IOException: ${exception.localizedMessage}")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("SearchPagingSource", "HttpException: ${exception.localizedMessage}")
            LoadResult.Error(exception)
        }
    }
}




// ------- write different paging sources for popular and top rated and ... movies-------
class PopularMoviesPagingSource(
    movieRepository: MovieRepository
) : BasePagingSource<Movie>(movieRepository::getPopularMovies)

class TopRatedMoviesPagingSource(
    movieRepository: MovieRepository
) : BasePagingSource<Movie>(movieRepository::getTopRatedMovies)

class UpcomingMoviesPagingSource(
    movieRepository: MovieRepository
) : BasePagingSource<Movie>(movieRepository::getUpComingMovies)

class SearchResultPagingSource(
    movieRepository: MovieRepository,
    query: String
) : SearchPagingSource<Movie>(repositoryCall = movieRepository::getSearchResult, query = query)