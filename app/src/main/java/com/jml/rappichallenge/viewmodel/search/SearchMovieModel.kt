package com.jml.rappichallenge.viewmodel.search

import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse

class SearchMovieModel {

    private var _list = ArrayList<Movie>()
    private var _lastPageStartPosition : Int? = null
    private var _page : Int? = null
    private var _pagesCount : Int = 0
    private var _totalCount : Int = 0
    private var _lastPage : ArrayList<Movie>? = null

    val list : List<Movie>
        get() = _list

    val pagesCount : Int
        get() = _pagesCount

    val totalCount : Int
        get() = _totalCount

    val isFinished: Boolean
        get(){
            if(_page == null) { return false }
            return _page!! >= pagesCount
        }

    val lastPage : List<Movie>
        get() = _lastPage ?: emptyList()

    val lastPageStart : Int
        get() = _lastPageStartPosition ?: 0

    fun append(movieSearchResponse: MovieSearchResponse) {

        _lastPageStartPosition = list.size

        _pagesCount = movieSearchResponse.pagesCount
        _totalCount = movieSearchResponse.totalResultCount
        _page = movieSearchResponse.page
        _lastPage = ArrayList(movieSearchResponse.result)

        val newData = movieSearchResponse.result

        if (!newData.isEmpty()) {
            _list.addAll(newData)
        }
    }

    fun clear() {
        _lastPageStartPosition = null
        _page = null
        _pagesCount = 0
        _totalCount = 0
        _lastPage = null
        _list.clear()
    }
}