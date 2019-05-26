package com.jml.rappichallenge.tools

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration





object RealmConfigurator {
    fun configureWithContext(context: Context) {

        Realm.init(context)
        val realmConfig = RealmConfiguration.Builder().schemaVersion(1).deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmConfig)

        var realm: Realm

        try {
            realm = Realm.getDefaultInstance()
            realm.close()
        } catch (e: Exception) {
            Realm.deleteRealm(realmConfig)
            //Realm file has been deleted.
            realm = Realm.getInstance(realmConfig)
            realm.close()
        }

    }
}