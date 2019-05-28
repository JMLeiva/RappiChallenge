package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.jml.rappichallenge.models.dtos.MovieSearchResponseDTO

import java.lang.reflect.Type
import java.util.ArrayList

class MovieSearchResponse internal constructor(internal val dto: MovieSearchResponseDTO) {

    val page: Int
        get() = dto.page

    val totalResultCount: Int
        get() = dto.total_results

    val pagesCount: Int
        get() = dto.total_pages

    val result: List<Movie>
        get() = dto.results?.map {movieDTO -> Movie(movieDTO) } ?: ArrayList()

    private var lastPageStartIndex : Int = 0

    class Deserializer : JsonDeserializer<MovieSearchResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): MovieSearchResponse {
            val dto = context.deserialize<MovieSearchResponseDTO>(json, MovieSearchResponseDTO::class.java)
            return MovieSearchResponse(dto)
        }
    }
}
