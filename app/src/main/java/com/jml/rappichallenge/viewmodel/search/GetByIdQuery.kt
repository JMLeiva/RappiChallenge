package com.jml.rappichallenge.viewmodel.search


import android.os.Parcel
import android.os.Parcelable
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.tools.LanguageHelper

class GetByIdQuery : Parcelable {

    var id: Int = 0
    var language: Language = LanguageHelper.userLanaguage

    protected constructor(pIn: Parcel) {
        id = pIn.readInt()
        language = Language.valueOf(pIn.readString() ?: Language.English.name)
    }

    constructor()

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(language.name)
    }

    class Builder {
        private var builderGetByIdQuery: GetByIdQuery = GetByIdQuery()

        fun id(id : Int) : Builder {
            builderGetByIdQuery.id = id
            return this
        }

        fun language(language: Language) : Builder {
            builderGetByIdQuery.language = language
            return this
        }

        fun build(): GetByIdQuery {
            val result = GetByIdQuery()
            result.id = builderGetByIdQuery.id
            result.language = builderGetByIdQuery.language

            return result
        }
    }

    companion object CREATOR : Parcelable.Creator<GetByIdQuery> {
        override fun createFromParcel(`in`: Parcel): GetByIdQuery {
            return GetByIdQuery(`in`)
        }

        override fun newArray(size: Int): Array<GetByIdQuery?> {
            return arrayOfNulls(size)
        }
    }
}
