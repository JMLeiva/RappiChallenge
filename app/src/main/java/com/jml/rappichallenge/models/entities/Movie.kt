package com.jml.rappichallenge.models.entities

import android.content.Context

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.jml.rappichallenge.models.dtos.MovieDTO
import com.jml.rappichallenge.models.tools.LanguageHelper

import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class Movie internal constructor(internal val dto: MovieDTO) {

    val id: Int
        get() = dto.id

    val voteCount: Float
        get() = dto.vote_count.toFloat()

    val voteAverage: Float?
        get() = dto.vote_average

    val title: String?
        get() = dto.title

    val popularity: Float
        get() = dto.popularity

    val posterPath: String?
        get() = dto.poster_path

    val backdropPath: String?
        get() = dto.backdrop_path

    val originalLanguageCode: String?
        get() = dto.original_language

    val originalTitle: String?
        get() = dto.original_title


    // TODO Generos
    //private List<Integer> genre_ids;

    val isAdult: Boolean
        get() = dto.adult

    val overview: String?
        get() = dto.overview

    val releaseDate: Date?
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            try {
                return dateFormat.parse(dto.release_date)
            } catch (e: ParseException) {
                return null
            }

        }

    val genres: List<Namable>
        get() = dto.genres?.map { namableDTO -> Namable(namableDTO) } ?: ArrayList()

    val budget: Int
        get() = dto.budget

    val revenue: Int
        get() = dto.revenue

    val homepage: String?
        get() = dto.homepage

    val imdbId: String?
        get() = dto.imdbId

    val runtime: Int
        get() = dto.runtime

    val productionCompanies: List<Namable>
        get() = dto.production_companies?.map { namableDTO -> Namable(namableDTO) } ?: ArrayList()

    val productionCountries: List<Namable>
        get() = dto.production_countries?.map { namableDTO -> Namable(namableDTO) } ?: ArrayList()

    val spokenLanguages: List<Namable>
        get() = dto.spoken_languages?.map { namableDTO -> Namable(namableDTO) } ?: ArrayList()

    val status: String?
        get() = dto.status

    val tagline: String?
        get() = dto.tagline

    fun hasVideo(): Boolean {
        return dto.video
    }

    class Deserializer : JsonDeserializer<Movie> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Movie {
            val dto = context.deserialize<MovieDTO>(json, MovieDTO::class.java)
            return Movie(dto)
        }
    }

    object FormatHelper {
        fun getFormmatedLanguage(context: Context, languageCode: String): String {
            val id = LanguageHelper.getLanguageResNameByCode(languageCode)

            return if (id == 0) {
                ""
            } else context.getString(id)

        }
    }
}
