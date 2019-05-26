package com.jml.rappichallenge.models.dtos

import io.realm.RealmObject

open class NamableDTO : RealmObject() {
    internal var name: String? = null
}