package com.jml.rappichallenge.models.dtos

import io.realm.RealmList
import io.realm.RealmObject

open class VideoResponseDTO : RealmObject() {
    internal var id: Int = 0
    internal var results: RealmList<VideoDTO> = RealmList()
}