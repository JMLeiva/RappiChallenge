package com.jml.rappichallenge.viewmodel.detail


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.models.enums.ErrorCode
import com.jml.rappichallenge.models.enums.RequestState
import com.jml.rappichallenge.repository.ErrorWrapper
import com.jml.rappichallenge.repository.movies.MoviesRepository
import com.jml.rappichallenge.tools.ConnectionManager
import com.jml.rappichallenge.viewmodel.common.EntityListState
import com.jml.rappichallenge.viewmodel.common.EntityListViewModel
import javax.inject.Inject


class VideoListViewModel @Inject constructor(application: Application, private val moviesRepository: MoviesRepository) : EntityListViewModel<VideoResponse>(application) {

    private var itemIdInput: MutableLiveData<Int>? = null
    private var currentResults: VideoResponse? = null

    val videoReponse: VideoResponse?
        get() = currentResults


    @Inject
    lateinit var connectionManager: ConnectionManager

    override fun stateForResult(input: VideoResponse?): EntityListState {
        return if (input != null) {
            if (input.result.isEmpty()) {
                EntityListState.NoResults
            } else {
                EntityListState.Successful
            }
        } else {
            if (!connectionManager.isInternetConnected) {
                EntityListState.NoConnection
            } else EntityListState.Error
        }
    }

    override fun createDataObservable(): LiveData<VideoResponse> {

        itemIdInput = MutableLiveData()

        return Transformations.switchMap<Int, VideoResponse>(itemIdInput!!) { id ->
            if(id == null){
                errorWrapperObservable.value = ErrorWrapper(ErrorCode.NotFound.code, "Not Found")
                requestStateObservable.value = RequestState.Failure
                return@switchMap MutableLiveData<VideoResponse>()
            }

            requestStateObservable.value = RequestState.Loading
            return@switchMap getTransformationLiveData(id)
        }
    }


    private fun getTransformationLiveData(id: Int): LiveData<VideoResponse> {

        val searchLiveData = moviesRepository.getVideos(id)

        return Transformations.map(searchLiveData) { input ->
            if (input.isSuccessfull) {
                currentResults = input.getData()
                requestStateObservable.value = RequestState.Success
                return@map currentResults
            } else {
                errorWrapperObservable.value = input.error
                requestStateObservable.value = RequestState.Failure
                return@map null
            }
        }
    }
    fun setItemId(itemId: Int?) {

        this.itemIdInput?.value = itemId
    }

    fun retry() {
        this.itemIdInput?.value = this.itemIdInput?.value
    }
}