package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.repository.BaseRepository
import com.jml.rappichallenge.repository.ResponseWrapper
import com.jml.rappichallenge.repository.api.MdbApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(api : MdbApi) : BaseRepository(api), MoviesRepository {

    fun search(searchQuery: SearchQuery): LiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.discoverMovie(searchQuery.language, searchQuery.sorting, searchQuery.page, buildCallback(data))

        return data
    }

    override fun getPopularMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getPopularMovies(searchQuery.language, searchQuery.page, buildCallback(data))

        return data
    }

    override fun getTopRatedMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getTopRatedMovies(searchQuery.language, searchQuery.page, buildCallback(data))

        return data
    }

    override fun getUpcomingMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getUpcomingMovies(searchQuery.language, searchQuery.page, buildCallback(data))

        return data
    }

    override fun getById(id : Int) : LiveData<ResponseWrapper<Movie>> {
        val data = MutableLiveData<ResponseWrapper<Movie>>()
        api.getMovie(id, buildCallback(data))
        return data
    }
}