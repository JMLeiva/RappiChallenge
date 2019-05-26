package com.jml.rappichallenge.view.youtube

import com.jml.rappichallenge.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class YouTubeActivityModule {

    @ContributesAndroidInjector(modules = [YouTubeFragmentModule::class])
    @FragmentScope
    internal abstract fun bindFragment(): YouTubeFragment
}