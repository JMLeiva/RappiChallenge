package com.jml.rappichallenge.models.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.jml.rappichallenge.models.dtos.NamableDTO

import java.lang.reflect.Type

class Namable internal constructor(private val dto: NamableDTO) {

    val name: String?
        get() = dto.name



    class Deserializer : JsonDeserializer<Namable> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Namable {
            val dto = context.deserialize<NamableDTO>(json, NamableDTO::class.java)
            return Namable(dto)
        }
    }
}
