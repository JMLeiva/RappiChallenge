package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.viewmodel.search.GetByIdQuery
import com.jml.rappichallenge.viewmodel.search.GetMoviesQuery
import com.jml.rappichallenge.repository.BaseRepository
import com.jml.rappichallenge.repository.ResponseWrapper
import com.jml.rappichallenge.repository.api.MdbApi
import javax.inject.Inject

class MoviesRepositoryApi @Inject constructor(val api : MdbApi) : BaseRepository(), MoviesRepository {

    override fun getPopularMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        return getPopularMoviesMutable(getMoviesQuery)
    }

    fun getPopularMoviesMutable(getMoviesQuery: GetMoviesQuery) : MutableLiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getPopularMovies(getMoviesQuery.language, getMoviesQuery.page, buildCallback(data))
        return data
    }

    override fun getTopRatedMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
       return getTopRatedMoviesMutable(getMoviesQuery)
    }

    fun getTopRatedMoviesMutable(getMoviesQuery: GetMoviesQuery) : MutableLiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getTopRatedMovies(getMoviesQuery.language, getMoviesQuery.page, buildCallback(data))
        return data
    }

    override fun getUpcomingMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
       return getUpcomingMoviesMutable(getMoviesQuery)
    }

    fun getUpcomingMoviesMutable(getMoviesQuery: GetMoviesQuery) : MutableLiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()
        api.getUpcomingMovies(getMoviesQuery.language, getMoviesQuery.page, buildCallback(data))

        return data
    }

    override fun getById(query : GetByIdQuery) : LiveData<ResponseWrapper<Movie>> {
        return getByIdMutable(query)
    }

    fun getByIdMutable(query : GetByIdQuery) : MutableLiveData<ResponseWrapper<Movie>> {
        val data = MutableLiveData<ResponseWrapper<Movie>>()
        api.getMovie(query.id, query.language, buildCallback(data))
        return data
    }

    override fun getVideos(query : GetByIdQuery): LiveData<ResponseWrapper<VideoResponse>> {
        return getVideosMutable(query)
    }

    fun getVideosMutable(query : GetByIdQuery): MutableLiveData<ResponseWrapper<VideoResponse>> {
        val data = MutableLiveData<ResponseWrapper<VideoResponse>>()
        api.getVideos(query.id, buildCallback(data))
        return data
    }
}