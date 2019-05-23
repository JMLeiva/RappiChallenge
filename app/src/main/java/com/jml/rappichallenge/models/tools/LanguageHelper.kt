package com.jml.rappichallenge.models.tools

import com.jml.rappichallenge.R

import androidx.annotation.StringRes

object LanguageHelper {
    @StringRes
    fun getLanguageResNameByCode(code: String): Int {
        // TODO usar locale para esto?
        return when (code) {
            "en" -> R.string.lan_english
            else -> 0
        }
    }
}
