package com.jml.rappichallenge.repository

import androidx.lifecycle.MutableLiveData
import com.jml.rappichallenge.repository.api.ApiCallback

abstract class BaseRepository(){



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
