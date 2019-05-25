package com.jml.rappichallenge.repository.api

import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting

internal object MdbRoutes {

    val RootUrl = "https://api.themoviedb.org/"

    internal object Version {
        const val V3 = "3"
    }

    internal object Path {
        const val Discover = "discover"
        const val Popular = "popular"
        const val TopRated = "top_rated"
        const val Upcoming = "upcoming"
        const val Movie = "movie"
    }

    internal object QKey {
        const val ApiKey = "api_key"
        const val Language = "language"
        const val Sorting = "sort_by"
        const val IncludeAdult = "include_adult"
        const val IncludeVideo = "include_video"
        const val Page = "page"
        const val Keyword = "with_keywords"
    }

    internal object LanguageCode {
        private const val EN_code = "en-US"
        private const val ES_code = "es-ES"

        fun fromLanguage(language: Language): String {
            when (language) {
                Language.English -> return EN_code
                Language.Spanish -> return ES_code
                else -> return EN_code
            }
        }
    }

    internal object SortingCode {
        private const val PopulariteCode = "popularity.desc"
        private const val RatingCode = "vote_average.desc"
        private const val DateCode = "primary_release_date.desc"

        fun fromSorting(sorting: Sorting): String {
            when (sorting) {
                Sorting.Popularity -> return PopulariteCode
                Sorting.Rating -> return RatingCode
                Sorting.Date -> return DateCode
                else -> return PopulariteCode
            }
        }
    }
}
