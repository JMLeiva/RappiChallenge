package com.jml.rappichallenge.repository.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.jml.rappichallenge.models.dtos.MovieDTO
import com.jml.rappichallenge.models.dtos.MovieSearchResponseDTO
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.VideoResponse
import com.jml.rappichallenge.models.enums.ErrorCode
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.repository.BaseRepository
import com.jml.rappichallenge.repository.ResponseWrapper
import com.jml.rappichallenge.tools.observeOnce
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import javax.inject.Inject

class MoviesRepositoryDisk @Inject constructor(val parentRepository: MoviesRepositoryApi, val realm : Realm) : BaseRepository(), MoviesRepository {

    private object Constants {
        val pageSize = 20
    }

    override fun getPopularMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        val liveData = parentRepository.getPopularMoviesMutable(searchQuery)

        liveData.observeOnce(object : Observer<ResponseWrapper<MovieSearchResponse>> {
            override fun onChanged(responseWrapper : ResponseWrapper<MovieSearchResponse>?) {
                if (responseWrapper?.isSuccessfull == true) {
                    saveMovieResponse(responseWrapper.getData())
                } else {
                    getSortedFromDisk("popularity", searchQuery.page) { responseWrapper ->
                        liveData.value = responseWrapper
                    }
                }
            }
        })

        return liveData
    }

    override fun getTopRatedMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {

        val liveData = parentRepository.getTopRatedMoviesMutable(searchQuery)

        liveData.observeOnce(object : Observer<ResponseWrapper<MovieSearchResponse>> {
            override fun onChanged(responseWrapper : ResponseWrapper<MovieSearchResponse>?) {
                if (responseWrapper?.isSuccessfull == true) {
                    saveMovieResponse(responseWrapper.getData())
                } else {
                    getSortedFromDisk("vote_average", searchQuery.page) { responseWrapper ->
                        liveData.value = responseWrapper
                    }
                }
            }
        })

        return liveData
    }

    override fun getUpcomingMovies(searchQuery: SearchQuery) : LiveData<ResponseWrapper<MovieSearchResponse>> {
        val liveData = parentRepository.getUpcomingMoviesMutable(searchQuery)

        liveData.observeOnce(object : Observer<ResponseWrapper<MovieSearchResponse>> {
            override fun onChanged(responseWrapper : ResponseWrapper<MovieSearchResponse>?) {
                if (responseWrapper?.isSuccessfull == true) {
                    saveMovieResponse(responseWrapper.getData())
                } else {
                    getSortedFromDisk("release_date", searchQuery.page) { responseWrapper ->
                        liveData.value = responseWrapper
                    }
                }
            }
        })

        return liveData
    }

    override fun getById(id : Int) : LiveData<ResponseWrapper<Movie>> {
        val liveData = parentRepository.getByIdMutable(id)

        liveData.observeOnce(object : Observer<ResponseWrapper<Movie>> {
            override fun onChanged(responseWrapper : ResponseWrapper<Movie>?) {
                if (responseWrapper?.isSuccessfull == true) {
                    saveMovie(responseWrapper.getData())
                } else {
                    getFromDisk(id) { responseWrapper ->
                        liveData.value = responseWrapper
                    }
                }
            }
        })

        return liveData
    }

    override fun getVideos(movieId: Int): LiveData<ResponseWrapper<VideoResponse>> {
        return parentRepository.getVideos(movieId)
    }

    private fun saveMovie(movie : Movie) {
        realm.executeTransaction { tRealm ->
            tRealm.copyToRealmOrUpdate(movie.dto)
        }
    }

    private fun saveMovieResponse(movieSearchResponse: MovieSearchResponse) {
        realm.executeTransaction { tRealm ->
            tRealm.copyToRealmOrUpdate(movieSearchResponse.dto.results)
        }
    }

    private fun getSortedFromDisk(sortingField :String, page : Int, callback : (ResponseWrapper<MovieSearchResponse>) -> Unit) {
        realm.executeTransaction { tRealm ->
            val dtos = tRealm.where<MovieDTO>(MovieDTO::class.java).sort(sortingField, Sort.DESCENDING).findAll()
            callback(dtosToMovieSearchResponse(page, dtos))
        }
    }

    private fun getFromDisk(id : Int, callback : (ResponseWrapper<Movie>) -> Unit) {
        realm.executeTransaction { tRealm ->
            val dto = tRealm.where<MovieDTO>(MovieDTO::class.java).equalTo("id", id).findFirst()

            if (dto == null) {
                callback(ResponseWrapper.errorResponse(ErrorCode.NotFound.ordinal, "Not Found"))
            } else {
                callback(ResponseWrapper.successfullResponse(Movie(dto)))
            }
        }
    }

    private fun dtosToMovieSearchResponse(page : Int, result : RealmResults<MovieDTO>) : ResponseWrapper<MovieSearchResponse> {

        val start = (page - 1) * Constants.pageSize
        var end =  start + Constants.pageSize

        if (end >= result.size) {
            end = result.size - 1
        }

        if(result.isEmpty() || start >= result.size) {
            return ResponseWrapper.errorResponse(ErrorCode.NotFound.ordinal, "Not Found")
        }

        val responseDTO = MovieSearchResponseDTO()

        responseDTO.page = page
        responseDTO.total_pages = (result.size / Constants.pageSize) + 2 // Nunca voy a cachear el 100% de los resultados
        responseDTO.total_results = result.size + 1
        responseDTO.results = ArrayList(result.subList(start, end))

        return ResponseWrapper.successfullResponse(MovieSearchResponse(responseDTO))
    }
}