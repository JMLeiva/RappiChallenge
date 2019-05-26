package com.jml.rappichallenge.repository


/*
 * Wraps the response (either successful or not) in a single object
 * */
class ResponseWrapper<T> private constructor() {
    private var data: T? = null
    private var errorWrapper: ErrorWrapper? = null
    var isSuccessfull: Boolean = false
        private set

    val error: ErrorWrapper?
        get() {
            if (isSuccessfull) {
                throw IllegalStateException("Can't retrieve error from a successfull repsonse")
            }

            return errorWrapper
        }

    init {
        isSuccessfull = false
    }

    fun getData(): T {
        if (!isSuccessfull) {
            throw IllegalStateException("Can't retrieve data from a non successfull repsonse")
        }

        return data!!
    }

    companion object {

        fun <T> successfullResponse(data: T): ResponseWrapper<T> {
            val result = ResponseWrapper<T>()
            result.data = data
            result.isSuccessfull = true
            return result
        }

        fun <T> errorResponse(code: Int, message: String?): ResponseWrapper<T> {
            val result = ResponseWrapper<T>()
            result.errorWrapper = ErrorWrapper(code, message)
            result.isSuccessfull = false
            return result
        }
    }
}
