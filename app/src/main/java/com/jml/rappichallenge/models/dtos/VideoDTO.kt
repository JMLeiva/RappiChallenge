package com.jml.rappichallenge.models.dtos

import io.realm.RealmObject

open class VideoDTO  : RealmObject() {
    internal var id: String = ""
    internal var key: String = ""
    internal var name: String = ""
    internal var site: String = ""
    internal var size: Int = 0
    internal var type: String = ""
}