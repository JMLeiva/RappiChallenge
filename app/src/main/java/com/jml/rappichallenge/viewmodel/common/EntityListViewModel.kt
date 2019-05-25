package com.jml.rappichallenge.viewmodel.common

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/*
 *	BaseViewModel for list entities. It has an adition state called "No Content"
 *  that insforms the requested list is empty
 * */
abstract class EntityListViewModel<T>(application: Application) :  BaseViewModel<T>(application) {

    var entityListStateMutableLiveData: LiveData<EntityListState>

    init {
        entityListStateMutableLiveData = Transformations.map(dataObservable) { input ->
            stateForResult(input)
        }

    }

    protected abstract fun stateForResult(input: T?): EntityListState
}
