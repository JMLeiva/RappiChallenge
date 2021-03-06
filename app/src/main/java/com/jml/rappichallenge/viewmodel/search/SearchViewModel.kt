package com.jml.rappichallenge.viewmodel.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.enums.RequestState
import com.jml.rappichallenge.models.enums.Sorting
import com.jml.rappichallenge.repository.movies.MoviesRepository
import com.jml.rappichallenge.tools.ConnectionManager
import com.jml.rappichallenge.viewmodel.common.EntityListState
import com.jml.rappichallenge.viewmodel.common.EntityListViewModel
import javax.inject.Inject


class SearchViewModel @Inject constructor(application: Application, private val moviesRepository: MoviesRepository) : EntityListViewModel<SearchMovieModel>(application) {


    private var getMoviesQueryInput: MutableLiveData<GetMoviesQuery>? = null
    private val rawQuery: GetMoviesQuery = GetMoviesQuery.Builder().sorting(Sorting.Popularity).build()
    private var currentResults: SearchMovieModel? = null


    @Inject
    lateinit var connectionManager : ConnectionManager

    override fun stateForResult(input: SearchMovieModel?): EntityListState {
        return if (input != null) {
            if (input.totalCount > 0) {
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

    override fun createDataObservable(): LiveData<SearchMovieModel> {
        getMoviesQueryInput = MutableLiveData()

        return Transformations.switchMap<GetMoviesQuery, SearchMovieModel>(getMoviesQueryInput!!) { query ->
            requestStateObservable.value = RequestState.Loading
            return@switchMap getTransformationLiveData(query)
        }
    }

    private fun getTransformationLiveData(query: GetMoviesQuery): LiveData<SearchMovieModel> {

        val searchLiveData = when(query.sorting) {
            Sorting.Popularity -> moviesRepository.getPopularMovies(query)
            Sorting.Rating -> moviesRepository.getTopRatedMovies(query)
            Sorting.Date -> moviesRepository.getUpcomingMovies(query)
        }

        return Transformations.map(searchLiveData) { input ->
            if (input.isSuccessfull) {

                if(currentResults == null)
                {
                    currentResults = SearchMovieModel()
                }

                currentResults!!.append(input.getData())

                requestStateObservable.value = RequestState.Success
                return@map currentResults
            } else {
                errorWrapperObservable.value = input.error
                requestStateObservable.value = RequestState.Failure
                return@map null
            }
        }
    }

    fun start() {
        this.getMoviesQueryInput?.value = rawQuery
    }

    fun advance() {
        if(currentResults == null) {
            start()
        }
        else {
            rawQuery.advancePage()
            this.getMoviesQueryInput?.value = rawQuery
        }
    }

    fun resetPaging() {
        currentResults = null
        rawQuery.resetPaging()
        this.getMoviesQueryInput?.value = rawQuery
    }

    var sorting: Sorting
    get() = rawQuery.sorting
    set(value) {

        if (rawQuery.sorting == value) { return }

        currentResults = null
        rawQuery.sorting = value
        resetPaging()
    }
}