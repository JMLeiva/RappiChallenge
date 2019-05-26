package com.jml.rappichallenge.models.dtos

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class VideoDTO  : RealmObject() {
    @PrimaryKey
    internal var id: String = ""
    internal var key: String = ""
    internal var name: String = ""
    internal var site: String = ""
    internal var size: Int = 0
    internal var type: String = ""
}