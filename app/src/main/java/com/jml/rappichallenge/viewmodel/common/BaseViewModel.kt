package com.jml.rappichallenge.viewmodel.common

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jml.rappichallenge.models.enums.RequestState


import android.app.Application
import com.jml.rappichallenge.repository.ErrorWrapper


/*
 * Base view model bases in a model-state change usability.
 * It informs when there was a change of state.
 * When state is "SUCCESSFUL", you can retrieve it's data with "getData()"
 *
 * It also provides information about the request-state, and errors.
 * */
abstract class BaseViewModel<T> internal constructor(application: Application) : AndroidViewModel(application) {

    internal val dataObservable : LiveData<T> by lazy { createDataObservable() }

    protected var requestStateObservable: MutableLiveData<RequestState> = MutableLiveData()
    protected var errorWrapperObservable: MutableLiveData<ErrorWrapper> = MutableLiveData()

    private val blindObserver: BlindObserver<T> = BlindObserver()

    val data: T?
        get() = dataObservable.value

    val stateObservable: LiveData<RequestState>
        get() = requestStateObservable

    val errorObservable: LiveData<ErrorWrapper>
        get() = errorWrapperObservable

    init {
        dataObservable.observeForever(blindObserver)
    }

    protected abstract fun createDataObservable(): LiveData<T>

    override fun onCleared() {
        super.onCleared()
        dataObservable.removeObserver(blindObserver)
    }

}