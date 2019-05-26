package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.repository.BaseRepository
import com.jml.rappichallenge.repository.ResponseWrapper
import com.jml.rappichallenge.repository.api.MdbApi
import javax.inject.Inject

class MoviesRepositoryApi @Inject constructor(val api : MdbApi) : BaseRepository(), MoviesRepository {

    override fun getPopularMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        return getPopularMoviesMutable(searchQuery)
    }

    fun getPopularMoviesMutable(searchQuery: SearchQuery) : MutableLiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getPopularMovies(searchQuery.language, searchQuery.page, buildCallback(data))
        return data
    }

    override fun getTopRatedMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
       return getTopRatedMoviesMutable(searchQuery)
    }

    fun getTopRatedMoviesMutable(searchQuery: SearchQuery) : MutableLiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getTopRatedMovies(searchQuery.language, searchQuery.page, buildCallback(data))
        return data
    }

    override fun getUpcomingMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
       return getUpcomingMoviesMutable(searchQuery)
    }

    fun getUpcomingMoviesMutable(searchQuery: SearchQuery) : MutableLiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getUpcomingMovies(searchQuery.language, searchQuery.page, buildCallback(data))

        return data
    }

    override fun getById(id : Int) : LiveData<ResponseWrapper<Movie>> {
        return getByIdMutable(id)
    }

    fun getByIdMutable(id : Int) : MutableLiveData<ResponseWrapper<Movie>> {
        val data = MutableLiveData<ResponseWrapper<Movie>>()
        api.getMovie(id, buildCallback(data))
        return data
    }

    override fun getVideos(movieId: Int): LiveData<ResponseWrapper<VideoResponse>> {
        return getVideosMutable(movieId)
    }

    fun getVideosMutable(movieId: Int): MutableLiveData<ResponseWrapper<VideoResponse>> {
        val data = MutableLiveData<ResponseWrapper<VideoResponse>>()
        api.getVideos(movieId, buildCallback(data))
        return data
    }
}