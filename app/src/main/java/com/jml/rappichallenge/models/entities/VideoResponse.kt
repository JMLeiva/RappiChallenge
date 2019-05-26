package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.ArrayList


class VideoResponse internal constructor(private val dto: DTO) {

    val id: Int
        get() = dto.id

    val result: List<Video>
        get() = ArrayList(dto.results)


    internal data class DTO (
            internal var id: Int,
            internal var results: MutableList<Video>
    )

    class Deserializer : JsonDeserializer<VideoResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): VideoResponse {
            val dto = context.deserialize<DTO>(json, DTO::class.java)
            return VideoResponse(dto)
        }
    }
}
