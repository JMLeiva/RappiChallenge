package com.jml.rappichallenge.viewmodel.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.enums.ErrorCode
import com.jml.rappichallenge.models.enums.RequestState
import com.jml.rappichallenge.repository.ErrorWrapper
import com.jml.rappichallenge.repository.movies.MoviesRepository
import com.jml.rappichallenge.tools.ConnectionManager
import com.jml.rappichallenge.viewmodel.common.EntityState
import com.jml.rappichallenge.viewmodel.common.EntityViewModel
import javax.inject.Inject

class MovieViewModel @Inject constructor(application: Application, private val moviesRepository: MoviesRepository) : EntityViewModel<Movie>(application) {

    @Inject
    lateinit var connectionManager : ConnectionManager

    private var itemIdInput: MutableLiveData<Int>? = null

    override fun createDataObservable(): LiveData<Movie> {
        itemIdInput = MutableLiveData()

        return Transformations.switchMap<Int, Movie>(itemIdInput!!) { id ->

            if(id == null){
                errorWrapperObservable.value = ErrorWrapper(ErrorCode.NotFound.code, "Not Found")
                requestStateObservable.value = RequestState.Failure
                return@switchMap MutableLiveData<Movie>()
            }

            requestStateObservable.value = RequestState.Loading
            return@switchMap getTransformationLiveData(id)
        }
    }

    private fun getTransformationLiveData(id: Int): LiveData<Movie> {

        val searchLiveData = moviesRepository.getById(id)

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

        this.itemIdInput?.value = itemId
    }

    fun retry() {
        this.itemIdInput?.value = this.itemIdInput?.value
    }
}