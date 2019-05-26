package com.jml.rappichallenge.tools

import kotlinx.coroutines.*


class Debouncer(val debounceDelay : Long) {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)


    var lastPushTimestamp : Long = 0

    var lastjob : Job? = null

    fun push(callback : () -> Unit) {
        lastjob?.cancel()

        lastjob = coroutineScope.launch {
            delay(debounceDelay)
            callback()
        }

    }
}