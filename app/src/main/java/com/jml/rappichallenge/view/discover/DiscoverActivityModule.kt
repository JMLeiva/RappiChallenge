package com.jml.rappichallenge.view.discover

import com.jml.rappichallenge.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DiscoverActivityModule {

    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    @FragmentScope
    internal abstract fun bindDisoverFragment(): DiscoverFragment
}