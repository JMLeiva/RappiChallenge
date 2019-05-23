package com.jml.rappichallenge.repository.api

import com.jml.rappichallenge.models.entities.MovieDiscoverResponse
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting


interface MdbApi {
    fun discoverMovie(language: Language, sorting: Sorting, page: Int, keyword: String?,
                      callback: ApiCallback<MovieDiscoverResponse>)
}
