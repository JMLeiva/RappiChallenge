package com.jml.rappichallenge.repository

import com.jml.rappichallenge.repository.api.MdbApi

abstract class BaseRepository(private val _api: MdbApi){

    val api: MdbApi
        get() = _api
}
