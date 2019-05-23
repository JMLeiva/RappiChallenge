package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import java.lang.reflect.Type

class Namable internal constructor(private val dto: DTO) {

    val name: String?
        get() = dto.name

    internal data class DTO (
        internal val name: String?
    )

    class Deserializer : JsonDeserializer<Namable> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Namable {
            val dto = context.deserialize<Namable.DTO>(json, Namable.DTO::class.java)
            return Namable(dto)
        }
    }
}
