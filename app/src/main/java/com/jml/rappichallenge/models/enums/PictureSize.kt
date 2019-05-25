package com.jml.rappichallenge.models.enums

object PictureSize {
    enum class Backdrop(val path : String) {
        w300("w300/"),
        w780 ("w780/"),
        w1080("w1080/"),
        original("original/")
    }

    enum class Poster(val path : String) {
        w45("w45/"),
        w92 ("w92/"),
        w154("w154/"),
        w185 ("w185/"),
        w300("w300/"),
        w500 ("w500/"),
        original("original/")
    }
}