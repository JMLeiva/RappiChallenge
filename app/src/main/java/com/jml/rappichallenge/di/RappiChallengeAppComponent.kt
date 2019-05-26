package com.jml.rappichallenge.di

import com.jml.rappichallenge.RappiChallengeApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    RappiChallengeAppModule::class,
    ViewModelModule::class,
    BuildersModule::class,
    RealmModule::class])

interface RappiChallengeAppComponent : AndroidInjector<RappiChallengeApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: RappiChallengeApplication): Builder
        fun build() : RappiChallengeAppComponent
    }
    override fun inject(app: RappiChallengeApplication)
}