package com.jml.rappichallenge.repository.api

import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting


interface MdbApi {
    fun discoverMovie(language: Language, sorting: Sorting, page: Int,
                      callback: ApiCallback<MovieSearchResponse>)

    fun getPopularMovies(language: Language, page : Int, callback: ApiCallback<MovieSearchResponse>)
    fun getTopRatedMovies(language: Language, page : Int, callback: ApiCallback<MovieSearchResponse>)
    fun getUpcomingMovies(language: Language, page : Int, callback: ApiCallback<MovieSearchResponse>)

    fun getMovie(id : Int, callback: ApiCallback<Movie>)
    fun getVideos(movieId: Int, buildCallback: ApiCallback<VideoResponse>)
}
