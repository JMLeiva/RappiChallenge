package com.jml.rappichallenge.models.tools

import com.jml.rappichallenge.models.enums.Language
import java.util.*

object LanguageHelper {

    fun getLanguagByCode(code: String): Language {

        return when (code) {
            "es" -> Language.Spanish
            "en" -> Language.English
            else -> Language.English
        }
    }

    val userLanaguage : Language
        get() = getLanguagByCode(Locale.getDefault().language)
}
