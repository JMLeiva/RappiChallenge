package com.jml.rappichallenge.viewmodel.common


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/*
 *	BaseViewModel normal entities
 * */
abstract class EntityViewModel<T>(application: Application) : BaseViewModel<T>(application) {
    var entityStateObserbale: LiveData<EntityState>
        protected set

    init {

        entityStateObserbale = Transformations.map(dataObservable) { input ->
            stateForResult(input)
        }
    }

    protected abstract fun stateForResult(input: T?): EntityState
}