package com.jml.rappichallenge.di

import com.jml.rappichallenge.di.scope.ActivityScope
import com.jml.rappichallenge.view.discover.DiscoverActivity
import com.jml.rappichallenge.view.discover.DiscoverActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [DiscoverActivityModule::class])
    @ActivityScope
    internal abstract fun bindDiscoverActivity(): DiscoverActivity

}