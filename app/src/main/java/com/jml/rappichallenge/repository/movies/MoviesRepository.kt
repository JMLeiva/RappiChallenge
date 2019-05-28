package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.viewmodel.search.GetByIdQuery
import com.jml.rappichallenge.viewmodel.search.GetMoviesQuery
import com.jml.rappichallenge.repository.ResponseWrapper

interface MoviesRepository {
    fun getPopularMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>>
    fun getTopRatedMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>>
    fun getUpcomingMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>>
    fun getById(query : GetByIdQuery) : LiveData<ResponseWrapper<Movie>>
    fun getVideos(query: GetByIdQuery): LiveData<ResponseWrapper<VideoResponse>>
}