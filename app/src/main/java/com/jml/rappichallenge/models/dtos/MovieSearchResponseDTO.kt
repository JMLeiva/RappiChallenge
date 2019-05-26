package com.jml.rappichallenge.models.dtos

open class MovieSearchResponseDTO  {
    internal var page: Int = 0
    internal var total_results: Int = 0
    internal var total_pages: Int = 0
    internal var results: MutableList<MovieDTO>? = null
}