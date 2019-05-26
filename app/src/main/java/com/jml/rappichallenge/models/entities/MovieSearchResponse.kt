package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.jml.rappichallenge.models.dtos.MovieSearchResponseDTO

import java.lang.reflect.Type
import java.util.ArrayList

class MovieSearchResponse internal constructor(private val dto: MovieSearchResponseDTO) {

    val page: Int
        get() = dto.page

    val totalResultCount: Int
        get() = dto.total_results

    val pagesCount: Int
        get() = dto.total_pages

    val isFinished: Boolean
        get() = page == pagesCount

    val result: List<Movie>
        get() = dto.results?.map {movieDTO -> Movie(movieDTO) } ?: ArrayList()

    fun append(data: MovieSearchResponse?) {

        if (data == null) {
            return
        }

        dto.page = data.page
        dto.total_pages = data.pagesCount
        dto.total_results = data.totalResultCount
        if (data.dto.results != null) {
            dto.results?.addAll(data.dto.results!!)
        }
    }



    class Deserializer : JsonDeserializer<MovieSearchResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): MovieSearchResponse {
            val dto = context.deserialize<MovieSearchResponseDTO>(json, MovieSearchResponseDTO::class.java)
            return MovieSearchResponse(dto)
        }
    }
}
