package com.jml.rappichallenge.viewmodel.search

import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse

class SearchMoviewModel {

    private var _list = ArrayList<Movie>()
    private var _lastPageStartPosition : Int? = null
    private var _pagesCount : Int = 0
    private var _totalCount : Int = 0

    val list : List<Movie>
        get() = _list

    val pagesCount : Int
        get() = _pagesCount

    val totalCount : Int
        get() = _totalCount

    val lastPageResults : List<Movie>
        get() {
            if(_lastPageStartPosition == null) { return emptyList() }
            return _list.subList(_lastPageStartPosition!!, _list.size - 1)
        }

    fun append(movieSearchResponse: MovieSearchResponse) {

        _lastPageStartPosition = list.size

        _pagesCount = movieSearchResponse.pagesCount
        _totalCount = movieSearchResponse.totalResultCount

        val newData = movieSearchResponse.result

        if (!newData.isEmpty()) {
            _list.addAll(newData)
        }
    }

    fun clear() {
        _lastPageStartPosition = null
        _pagesCount = 0
        _totalCount = 0
        _list.clear()
    }
}