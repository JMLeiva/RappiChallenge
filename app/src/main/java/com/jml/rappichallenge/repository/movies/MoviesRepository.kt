package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.repository.BaseRepository
import com.jml.rappichallenge.repository.ResponseWrapper
import com.jml.rappichallenge.repository.api.ApiCallback
import com.jml.rappichallenge.repository.api.MdbApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor( api : MdbApi) : BaseRepository(api) {

    fun search(searchQuery: SearchQuery): LiveData<ResponseWrapper<MovieSearchResponse>> {
        val data = MutableLiveData<ResponseWrapper<MovieSearchResponse>>()

        api.discoverMovie(searchQuery.language, searchQuery.sorting, searchQuery.page, object : ApiCallback<MovieSearchResponse> {
            override fun onResponse(response: MovieSearchResponse) {
               // updateMemoryCache(response.getResults())
                data.setValue(ResponseWrapper.successfullResponse(response))
            }

            override fun onFailure(code: Int, message: String?) {
                data.setValue(ResponseWrapper.errorResponse(code, message))
            }
        })

        return data
    }
}