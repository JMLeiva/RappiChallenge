package com.jml.rappichallenge.repository.memory

class ExpiryPolicy(val durationInMillis : Long) {

    private var initialTimeInMillis : Long

    init {
        initialTimeInMillis = System.currentTimeMillis()
    }

    fun isExpired() : Boolean {
        // < 0 means eternal
        if(durationInMillis < 0) {
            return false
        }

       return (System.currentTimeMillis() - initialTimeInMillis) > durationInMillis
    }

    companion object Builder {
        fun buildEternal() : ExpiryPolicy {
            return ExpiryPolicy(-1)
        }

        fun buildWithSeconds(seconds : Long) : ExpiryPolicy {
            return ExpiryPolicy(seconds * 1000)
        }

        fun buildWithMinutes(minutes : Long) : ExpiryPolicy {
            return ExpiryPolicy(minutes * 1000 * 60)
        }

        fun buildWithHours(hours : Long) : ExpiryPolicy {
            return ExpiryPolicy(hours * 1000 * 60 * 24)
        }
    }
}