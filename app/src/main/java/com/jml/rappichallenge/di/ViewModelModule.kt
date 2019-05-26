package com.jml.rappichallenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jml.rappichallenge.viewmodel.detail.MovieViewModel
import com.jml.rappichallenge.viewmodel.detail.VideoListViewModel
import com.jml.rappichallenge.viewmodel.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/*ViewModel DI Providers */

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun bindMovieViewModel(searchViewModel: MovieViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(VideoListViewModel::class)
    internal abstract fun bindVideoListViewModel(searchViewModel: VideoListViewModel): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: RappiChallengeViewModelProviderFactory): ViewModelProvider.Factory
}
