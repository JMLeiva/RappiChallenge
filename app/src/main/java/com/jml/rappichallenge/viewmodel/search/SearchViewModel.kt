package com.jml.rappichallenge.viewmodel.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.RequestState
import com.jml.rappichallenge.models.enums.Sorting
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.repository.movies.MoviesRepository
import com.jml.rappichallenge.tools.ConnectionManager
import com.jml.rappichallenge.viewmodel.common.EntityListState
import com.jml.rappichallenge.viewmodel.common.EntityListViewModel
import javax.inject.Inject


class SearchViewModel @Inject constructor(application: Application, moviesRepository: MoviesRepository) : EntityListViewModel<MovieSearchResponse?>(application) {


    private var searchQueryInput: MutableLiveData<SearchQuery>? = null
    private val itemRepository: MoviesRepository = moviesRepository
    private val rawQuery: SearchQuery = SearchQuery.Builder().sorting(Sorting.Popularity).language(Language.English).build()
    private var currentResults: MovieSearchResponse? = null

    val query : LiveData<SearchQuery>?
    get() = this.searchQueryInput

    val searchResponse : MovieSearchResponse?
    get() = currentResults

    @Inject
    lateinit var connectionManager : ConnectionManager

    override fun stateForResult(input: MovieSearchResponse?): EntityListState {
        return if (input != null) {
            if (input.totalResultCount > 0) {
                EntityListState.Successful
            } else {
                EntityListState.NoResults
            }
        } else {
            if (!connectionManager.isInternetConnected) {
                EntityListState.NoConnection
            } else EntityListState.Error
        }
    }

    override fun createDataObservable(): LiveData<MovieSearchResponse?> {
        searchQueryInput = MutableLiveData()

        return Transformations.switchMap<SearchQuery, MovieSearchResponse?>(searchQueryInput!!) { query ->
            requestStateObservable.value = RequestState.Loading
            return@switchMap getTransformationLiveData(query)
        }
    }

    private fun getTransformationLiveData(query: SearchQuery): LiveData<MovieSearchResponse?> {

        val searchLiveData = itemRepository.search(query)

        return Transformations.map(searchLiveData) { input ->
            if (input.isSuccessfull) {
                currentResults = input.getData()
                requestStateObservable.setValue(RequestState.Success)
                return@map input.getData()
            } else {
                errorWrapperObservable.setValue(input.error)
                requestStateObservable.setValue(RequestState.Failure)
                return@map null
            }
        }
    }

    fun start() {
        this.searchQueryInput?.value = rawQuery
    }

    fun advance() {
        rawQuery.advancePage()
        this.searchQueryInput?.value = rawQuery
    }
}