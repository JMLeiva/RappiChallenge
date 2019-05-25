package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import java.lang.reflect.Type
import java.util.ArrayList

class MovieSearchResponse internal constructor(private val dto: DTO) {

    val page: Int
        get() = dto.page

    val totalResultCount: Int
        get() = dto.total_results

    val pagesCount: Int
        get() = dto.total_pages

    val isFinished: Boolean
        get() = page == pagesCount

    val result: List<Movie>
        get() = ArrayList(dto.results!!)

    internal data class DTO (
        internal val page: Int = 0,
        internal val total_results: Int = 0,
        internal val total_pages: Int = 0,
        internal val results: List<Movie>? = null
    )

    class Deserializer : JsonDeserializer<MovieSearchResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): MovieSearchResponse {
            val dto = context.deserialize<DTO>(json, MovieSearchResponse.DTO::class.java)
            return MovieSearchResponse(dto)
        }
    }
}
