package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.jml.rappichallenge.models.enums.VideoSite
import java.lang.reflect.Type


class Video internal constructor(private val dto: DTO) {

    val key: String
        get() = dto.key

    val id: String
        get() = dto.id

    val name: String
        get() = dto.name

    val size: Int
        get() = dto.size

    val site : VideoSite
        get () {
            return when (dto.site) {
                VideoSite.YouTube.siteName -> VideoSite.YouTube
                else -> VideoSite.Other
            }
        }

    internal data class DTO (
            internal val id: String,
            internal val key: String,
            internal val name: String,
            internal val site: String,
            internal val size: Int,
            internal val type: String
    )

    class Deserializer : JsonDeserializer<Video> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Video {
            val dto = context.deserialize<DTO>(json, DTO::class.java)
            return Video(dto)
        }
    }
}
