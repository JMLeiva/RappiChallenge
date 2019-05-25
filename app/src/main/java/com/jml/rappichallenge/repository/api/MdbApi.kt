package com.jml.rappichallenge.repository.api

import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting


interface MdbApi {
    fun discoverMovie(language: Language, sorting: Sorting, page: Int,
                      callback: ApiCallback<MovieSearchResponse>)
}
