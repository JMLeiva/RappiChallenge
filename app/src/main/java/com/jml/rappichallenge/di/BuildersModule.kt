package com.jml.rappichallenge.di

import com.jml.rappichallenge.di.scope.ActivityScope
import com.jml.rappichallenge.view.detail.DetailActivity
import com.jml.rappichallenge.view.detail.DetailActivityModule
import com.jml.rappichallenge.view.search.SearchActivity
import com.jml.rappichallenge.view.search.SearchActivityModule
import com.jml.rappichallenge.view.youtube.YouTubeActivity
import com.jml.rappichallenge.view.youtube.YouTubeActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [SearchActivityModule::class])
    @ActivityScope
    internal abstract fun bindSearchActivity(): SearchActivity

    @ContributesAndroidInjector(modules = [DetailActivityModule::class])
    @ActivityScope
    internal abstract fun bindDetailActivity(): DetailActivity

    @ContributesAndroidInjector(modules = [YouTubeActivityModule::class])
    @ActivityScope
    internal abstract fun bindYouTubeActivity(): YouTubeActivity

}