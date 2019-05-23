package com.jml.rappichallenge.repository.api

/* Callback for API responses */
interface ApiCallback<T> {
    fun onResponse(response: T)
    fun onFailure(code: Int, message: String?)
}
