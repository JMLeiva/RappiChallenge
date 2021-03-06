package com.jml.rappichallenge.di

import android.app.Application
import com.jml.rappichallenge.RappiChallengeApplication
import com.jml.rappichallenge.repository.api.MdbApi
import com.jml.rappichallenge.repository.api.MdbApiImpl
import com.jml.rappichallenge.repository.movies.MoviesRepository
import com.jml.rappichallenge.repository.movies.MoviesRepositoryMemory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RappiChallengeAppModule {

    @Binds
    @Singleton
    abstract fun bindsApplication(application : RappiChallengeApplication) : Application

    @Binds
    abstract fun bindsMovieRepository(moviesRepository: MoviesRepositoryMemory) : MoviesRepository

    @Binds
    abstract fun bindsMdbApi(api : MdbApiImpl) : MdbApi
}