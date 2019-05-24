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
    BuildersModule::class,
    ViewModelModule::class])
interface RappiChallengeAppComponent : AndroidInjector<RappiChallengeApplication> {

    override fun inject(app: RappiChallengeApplication)
}