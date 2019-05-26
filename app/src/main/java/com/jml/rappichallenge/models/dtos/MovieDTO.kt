package com.jml.rappichallenge.models.dtos

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MovieDTO : RealmObject() {
    @PrimaryKey
    internal var id: Int = 0
    internal var vote_count: Int = 0
    internal var video: Boolean = false
    internal var vote_average: Float? = null
    internal var title: String? = null
    internal var popularity: Float = 0.toFloat()
    internal var poster_path: String? = null
    internal var backdrop_path: String? = null
    internal var original_language: String? = null
    internal var original_title: String? = null
    internal var genres: RealmList<NamableDTO>? = null
    internal var adult: Boolean = false
    internal var overview: String? = null
    internal var release_date: String? = null
    internal var budget: Int = 0
    internal var revenue: Int = 0
    internal var homepage: String? = null
    internal var imdbId: String? = null
    internal var runtime: Int = 0
    internal var production_companies: RealmList<NamableDTO>? = null // TODO <- DTO mas completo tiene imagen
    internal var production_countries: RealmList<NamableDTO>? = null
    internal var spoken_languages: RealmList<NamableDTO>? = null
    internal var status: String? = null
    internal var tagline: String? = null
}