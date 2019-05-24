package com.jml.rappichallenge.viewmodel.common

import androidx.annotation.Nullable
import androidx.lifecycle.Observer

/*
 * Useful to observe LiveData observables in which other LiveData are backed using tansformation
 * */
class BlindObserver<T> : Observer<T> {
    override fun onChanged(@Nullable t: T) {
        // Do absolutely nothing
    }
}
