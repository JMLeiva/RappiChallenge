package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.repository.ResponseWrapper

interface MoviesRepository {
    fun getPopularMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>>
    fun getTopRatedMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>>
    fun getUpcomingMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>>
    fun getById(id : Int) : LiveData<ResponseWrapper<Movie>>
    fun getVideos(movieId: Int): LiveData<ResponseWrapper<VideoResponse>>
}