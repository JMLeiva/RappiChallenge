package com.jml.rappichallenge.di

import com.jml.rappichallenge.di.scope.ActivityScope
import com.jml.rappichallenge.view.search.SearchActivity
import com.jml.rappichallenge.view.search.SearchActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [SearchActivityModule::class])
    @ActivityScope
    internal abstract fun bindSearchActivity(): SearchActivity

}