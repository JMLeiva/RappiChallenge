package com.jml.rappichallenge.view.detail

import com.jml.rappichallenge.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DetailActivityModule {

    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    internal abstract fun bindFragment(): DetailFragment
}