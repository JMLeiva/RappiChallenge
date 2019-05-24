package com.jml.rappichallenge.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/*ViewModel DI Providers */

@Module
internal abstract class ViewModelModule {

   /* @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchTermViewModel::class)
    internal abstract fun bindSearchTermViewModel(searchTermViewModel: SearchTermViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ItemViewModel::class)
    internal abstract fun bindItemModel(itemViewModel: ItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DescriptionViewModel::class)
    internal abstract fun bindDescriptionViewModel(itemViewModel: DescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SitesViewModel::class)
    internal abstract fun bindSitesViewModel(sitesViewModel: SitesViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: MeLiViewModelProviderFactory): ViewModelProvider.Factory
*/

}
