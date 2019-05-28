package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.viewmodel.search.GetByIdQuery
import com.jml.rappichallenge.viewmodel.search.GetMoviesQuery
import com.jml.rappichallenge.repository.BaseRepository
import com.jml.rappichallenge.repository.ResponseWrapper
import com.jml.rappichallenge.repository.memory.ExpiryPolicy
import com.jml.rappichallenge.repository.memory.MovieMemoryCache
import com.jml.rappichallenge.repository.memory.VideoResponseMemoryCache
import com.jml.rappichallenge.tools.observeOnce
import javax.inject.Inject

class MoviesRepositoryMemory @Inject constructor(val movieMemoryCache : MovieMemoryCache, val videosMemoryCache: VideoResponseMemoryCache, val parentRepository: MoviesRepositoryDisk) : BaseRepository(), MoviesRepository {

    companion object {
        val memoryCacheSuffix : String = "MOVIE_"
    }

    override fun getPopularMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        return parentRepository.getPopularMovies(getMoviesQuery)
    }

    override fun getTopRatedMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        return parentRepository.getTopRatedMovies(getMoviesQuery)
    }

    override fun getUpcomingMovies(getMoviesQuery: GetMoviesQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        return parentRepository.getUpcomingMovies(getMoviesQuery)
    }

    override fun getById(query : GetByIdQuery) : LiveData<ResponseWrapper<Movie>> {

        val movie = movieMemoryCache.load(toMemoryCacheKey(query.id))

        if(movie != null) {
            val response = MutableLiveData<ResponseWrapper<Movie>>()
            response.value = ResponseWrapper.successfullResponse(movie)
            return response
        }

        val liveData = parentRepository.getById(query)

        liveData.observeOnce( object : Observer<ResponseWrapper<Movie>> {
            override fun onChanged(responseWrapper: ResponseWrapper<Movie>?) {
                if (responseWrapper?.isSuccessfull == true) {
                    movieMemoryCache.save(responseWrapper.getData(), toMemoryCacheKey(query.id), ExpiryPolicy.buildWithMinutes(30))
                }
            }
        })



        return liveData
    }

    override fun getVideos(query : GetByIdQuery): LiveData<ResponseWrapper<VideoResponse>> {

        val videoResponse = videosMemoryCache.load(toMemoryCacheKey(query.id))

        if(videoResponse != null) {
            val response = MutableLiveData<ResponseWrapper<VideoResponse>>()
            response.value = ResponseWrapper.successfullResponse(videoResponse)
            return response
        }

        val liveData =  parentRepository.getVideos(query)

        liveData.observeOnce( object : Observer<ResponseWrapper<VideoResponse>> {
            override fun onChanged(responseWrapper: ResponseWrapper<VideoResponse>?) {
                liveData.removeObserver(this)
                if (responseWrapper?.isSuccessfull == true) {
                    videosMemoryCache.save(liveData.value!!.getData(), toMemoryCacheKey(query.id), ExpiryPolicy.buildWithMinutes(30))
                }
            }
        })

        return liveData
    }

    private fun toMemoryCacheKey(id : Int) : String {
        return String.format("%s%d", memoryCacheSuffix, id)
    }
}