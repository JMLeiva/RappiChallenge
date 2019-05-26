package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.jml.rappichallenge.models.dtos.VideoResponseDTO
import java.lang.reflect.Type

class VideoResponse internal constructor(private val dto: VideoResponseDTO) {

    val id: Int
        get() = dto.id

    val result: List<Video>
        get() = dto.results.map { videoDTO -> Video(videoDTO) }

    class Deserializer : JsonDeserializer<VideoResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): VideoResponse {
            val dto = context.deserialize<VideoResponseDTO>(json, VideoResponseDTO::class.java)
            return VideoResponse(dto)
        }
    }
}
