package com.jml.rappichallenge.models.dtos

import io.realm.RealmList
import io.realm.RealmObject

open class MovieSearchResponseDTO : RealmObject() {
    internal var page: Int = 0
    internal var total_results: Int = 0
    internal var total_pages: Int = 0
    internal var results: RealmList<MovieDTO>? = null
}