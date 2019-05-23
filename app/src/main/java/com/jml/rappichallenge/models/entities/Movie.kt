package com.jml.rappichallenge.models.entities

import android.content.Context

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.jml.rappichallenge.models.tools.LanguageHelper

import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class Movie internal constructor(private val dto: DTO) {

    val id: Int
        get() = dto.id

    val voteCount: Float
        get() = dto.vote_count.toFloat()

    val voteAverage: Float
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
        get() = ArrayList(dto.genres!!)

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
        get() = ArrayList(dto.production_companies!!)

    val productionCountries: List<Namable>
        get() = ArrayList(dto.production_countries!!)

    val spokenLanguages: List<Namable>
        get() = ArrayList(dto.spoken_languages!!)

    val status: String?
        get() = dto.status

    val tagline: String?
        get() = dto.tagline

    fun hasVideo(): Boolean {
        return dto.video
    }

    internal data class DTO (
            internal val vote_count: Int = 0,
            internal val id: Int = 0,
            internal val video: Boolean = false,
            internal val vote_average: Float = 0.toFloat(),
            internal val title: String? = null,
            internal val popularity: Float = 0.toFloat(),
            internal val poster_path: String? = null,
            internal val backdrop_path: String? = null,
            internal val original_language: String? = null,
            internal val original_title: String? = null,
            internal val genres: List<Namable>? = null,
            internal val adult: Boolean = false,
            internal val overview: String? = null,
            internal val release_date: String? = null,
            internal val budget: Int = 0,
            internal val revenue: Int = 0,
            internal val homepage: String? = null,
            internal val imdbId: String? = null,
            internal val runtime: Int = 0,
            internal val production_companies: List<Namable>? = null, // TODO <- DTO mas completo tiene imagen
            internal val production_countries: List<Namable>? = null,
            internal val spoken_languages: List<Namable>? = null,
            internal val status: String? = null,
            internal val tagline: String? = null
    )

    class Deserializer : JsonDeserializer<Movie> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Movie {
            val dto = context.deserialize<Movie.DTO>(json, Movie.DTO::class.java)
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
