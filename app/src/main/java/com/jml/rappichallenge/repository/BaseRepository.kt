package com.jml.rappichallenge.repository

import androidx.lifecycle.MutableLiveData
import com.jml.rappichallenge.repository.api.ApiCallback
import com.jml.rappichallenge.repository.api.MdbApi

abstract class BaseRepository(private val _api: MdbApi){

    val api: MdbApi
        get() = _api

    fun <T>buildCallback(data : MutableLiveData<ResponseWrapper<T>>) : ApiCallback<T> {
        return (object : ApiCallback<T> {
            override fun onResponse(response: T) {
                // updateMemoryCache(response.getResults())
                data.setValue(ResponseWrapper.successfullResponse(response))
            }

            override fun onFailure(code: Int, message: String?) {
                data.setValue(ResponseWrapper.errorResponse(code, message))
            }
        })
    }
}
