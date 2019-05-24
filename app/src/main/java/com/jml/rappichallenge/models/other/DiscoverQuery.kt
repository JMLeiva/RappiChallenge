package com.jml.rappichallenge.models.other

import android.os.Parcel
import android.os.Parcelable
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting

class SearchQuery : Parcelable {

    var keyword: String? = null
        private set

    var page: Int = 1
        private set

    var sorting: Sorting = Sorting.Popularity
        private set

    var language: Language = Language.English
        private set


    protected constructor()

    protected constructor(pIn: Parcel) {
        keyword = pIn.readString()
        page = pIn.readInt()
        sorting = Sorting.valueOf(pIn.readString() ?: Sorting.Popularity.name)
        language = Language.valueOf(pIn.readString() ?: Language.English.name)
    }

    fun advancePage() {
        this.page += 1
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(keyword)
        dest.writeInt(page)
        dest.writeString(sorting.name)
        dest.writeString(language.name)
    }

    class Builder {
        internal var builderSearchQuuery: SearchQuery

        init {
            builderSearchQuuery = SearchQuery()
        }

        fun keyword(keyword: String): Builder {
            builderSearchQuuery.keyword = keyword
            return this
        }

        fun sorting(sorting : Sorting) : Builder {
            builderSearchQuuery.sorting = sorting
            return this
        }

        fun language(language: Language) : Builder {
            builderSearchQuuery.language = language
            return this
        }

        fun build(): SearchQuery {
            val result = SearchQuery()
            result.keyword = builderSearchQuuery.keyword
            result.page = builderSearchQuuery.page
            result.sorting = builderSearchQuuery.sorting
            result.language = builderSearchQuuery.language

            return result
        }
    }

    companion object CREATOR : Parcelable.Creator<SearchQuery> {
        override fun createFromParcel(`in`: Parcel): SearchQuery {
            return SearchQuery(`in`)
        }

        override fun newArray(size: Int): Array<SearchQuery?> {
            return arrayOfNulls(size)
        }
    }
}
