package com.jml.rappichallenge.view.search

import com.jml.rappichallenge.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SearchActivityModule {

    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    @FragmentScope
    internal abstract fun bindFragment(): SearchFragment
}