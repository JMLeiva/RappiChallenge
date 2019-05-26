package com.jml.rappichallenge.di

import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
object RealmModule {

    @JvmStatic
    @Provides
    fun providesRealm(): Realm = Realm.getDefaultInstance()
}