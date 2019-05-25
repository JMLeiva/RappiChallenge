package com.jml.rappichallenge.tools

import android.net.Uri
import com.jml.rappichallenge.models.enums.PictureSize

object PictureUrlBuilder {

    private val baseUrl = "https://image.tmdb.org/t/p/"

    fun buildBackdropUrl(path : String?, size : PictureSize.Backdrop) : Uri?{
        if(path == null) return null
        return Uri.parse(String.format("%s%s%s", baseUrl, size, path))
    }
    fun buildPosterUrl(path : String?, size : PictureSize.Poster) : Uri?{
        if(path == null) return null
        return Uri.parse(String.format("%s%s%s", baseUrl, size, path))
    }
}