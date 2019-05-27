package com.jml.rappichallenge.models.other

import android.os.Parcel
import android.os.Parcelable
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting
import com.jml.rappichallenge.models.tools.LanguageHelper

class GetMoviesQuery : Parcelable {

    var page: Int = 1
        private set

    var sorting: Sorting = Sorting.Popularity
    var language: Language = LanguageHelper.userLanaguage

    protected constructor(pIn: Parcel) {
        page = pIn.readInt()
        sorting = Sorting.valueOf(pIn.readString() ?: Sorting.Popularity.name)
        language = Language.valueOf(pIn.readString() ?: Language.English.name)
    }

    constructor()

    fun advancePage() {
        this.page += 1
    }

    fun resetPaging() {
        this.page = 1
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(page)
        dest.writeString(sorting.name)
        dest.writeString(language.name)
    }

    class Builder {
        private var builderGetMoviesQuery: GetMoviesQuery = GetMoviesQuery()

        fun sorting(sorting : Sorting) : Builder {
            builderGetMoviesQuery.sorting = sorting
            return this
        }

        fun language(language: Language) : Builder {
            builderGetMoviesQuery.language = language
            return this
        }

        fun build(): GetMoviesQuery {
            val result = GetMoviesQuery()
            result.page = builderGetMoviesQuery.page
            result.sorting = builderGetMoviesQuery.sorting
            result.language = builderGetMoviesQuery.language

            return result
        }
    }

    companion object CREATOR : Parcelable.Creator<GetMoviesQuery> {
        override fun createFromParcel(`in`: Parcel): GetMoviesQuery {
            return GetMoviesQuery(`in`)
        }

        override fun newArray(size: Int): Array<GetMoviesQuery?> {
            return arrayOfNulls(size)
        }
    }
}
