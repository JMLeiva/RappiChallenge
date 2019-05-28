package com.jml.rappichallenge.viewmodel.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.enums.ErrorCode
import com.jml.rappichallenge.models.enums.RequestState
import com.jml.rappichallenge.viewmodel.search.GetByIdQuery
import com.jml.rappichallenge.repository.ErrorWrapper
import com.jml.rappichallenge.repository.movies.MoviesRepository
import com.jml.rappichallenge.tools.ConnectionManager
import com.jml.rappichallenge.viewmodel.common.EntityState
import com.jml.rappichallenge.viewmodel.common.EntityViewModel
import javax.inject.Inject

class MovieViewModel @Inject constructor(application: Application, private val moviesRepository: MoviesRepository) : EntityViewModel<Movie>(application) {

    @Inject
    lateinit var connectionManager : ConnectionManager

    private var itemIdInput: MutableLiveData<GetByIdQuery>? = null
    private var rawIdInput = GetByIdQuery()

    override fun createDataObservable(): LiveData<Movie> {
        itemIdInput = MutableLiveData()

        return Transformations.switchMap<GetByIdQuery, Movie>(itemIdInput!!) { id ->

            if(id == null){
                errorWrapperObservable.value = ErrorWrapper(ErrorCode.NotFound.code, "Not Found")
                requestStateObservable.value = RequestState.Failure
                return@switchMap MutableLiveData<Movie>()
            }

            requestStateObservable.value = RequestState.Loading
            return@switchMap getTransformationLiveData(id)
        }
    }

    private fun getTransformationLiveData(query: GetByIdQuery): LiveData<Movie> {

        val searchLiveData = moviesRepository.getById(query)

        return Transformations.map(searchLiveData) { input ->
            if (input.isSuccessfull) {

                requestStateObservable.value = RequestState.Success
                return@map input.getData()
            } else {
                errorWrapperObservable.value = input.error
                requestStateObservable.value = RequestState.Failure
                return@map null
            }
        }
    }

    override fun stateForResult(input: Movie?): EntityState {
        return if (input != null) {
            EntityState.Successful
        } else {
            if (!connectionManager.isInternetConnected) {
                EntityState.NoConnection
            } else EntityState.Error
        }
    }

    fun setItemId(itemId: Int?) {
        if(itemId == null) {
            return
        }

        rawIdInput.id = itemId
        this.itemIdInput?.value = rawIdInput
    }

    fun retry() {
        this.itemIdInput?.value = this.itemIdInput?.value
    }
}