package com.jml.rappichallenge.repository.api

import com.google.gson.GsonBuilder
import com.jml.rappichallenge.BuildConfig
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.MovieSearchResponse
import com.jml.rappichallenge.models.entities.Namable
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MdbApiImpl @Inject constructor() : MdbApi {

    private val service: Service

    private interface Service {
        @GET(MdbRoutes.Version.V3 + "/" + MdbRoutes.Path.Discover + "/" + MdbRoutes.Path.Movie)
        fun search(@Query(MdbRoutes.QKey.ApiKey) apiKey: String,
                   @Query(MdbRoutes.QKey.Language) language: String,
                   @Query(MdbRoutes.QKey.Sorting) sorting: String,
                   @Query(MdbRoutes.QKey.IncludeAdult) includeAdult: Boolean,
                   @Query(MdbRoutes.QKey.IncludeVideo) includeVideo: Boolean,
                   @Query(MdbRoutes.QKey.Page) page: Int): Call<MovieSearchResponse>

    }

    init {
        this.service = createService()
    }

    private fun createService(): Service {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(20, SECONDS)
                .connectTimeout(20, SECONDS)

        val okHttpClient = okHttpClientBuilder.build()

        val builder = GsonBuilder()
                .registerTypeAdapter(Namable::class.java, Namable.Deserializer())
                .registerTypeAdapter(Movie::class.java, Movie.Deserializer())
                .registerTypeAdapter(MovieSearchResponse::class.java, MovieSearchResponse.Deserializer())

        return Retrofit.Builder()
                .baseUrl(MdbRoutes.RootUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build()
                .create(Service::class.java)
    }

    override fun discoverMovie(language: Language, sorting: Sorting, page: Int,
                               callback: ApiCallback<MovieSearchResponse>) {
        val call = service.search(BuildConfig.ApiKey,
                MdbRoutes.LanguageCode.fromLanguage(language),
                MdbRoutes.SortingCode.fromSorting(sorting),
                false,
                true, page)

        makeCall(call, callback)
    }

    private fun <T> makeCall(call: Call<T>, callback: ApiCallback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {

                    val responseBody = response.body()

                    if(responseBody != null) {
                        callback.onResponse(responseBody)
                    }
                    else {
                        callback.onFailure(-1, "Response is null")
                    }
                } else {
                    var errorMessage = ""

                    try {
                        val errprResponse = response.errorBody()

                        if (errprResponse != null) {
                            errorMessage = errprResponse.string()
                        }
                    } catch (e: IOException) {
                        // IGNORE
                    }

                    callback.onFailure(response.code(), errorMessage)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFailure(0, t.message)
            }
        })
    }
}
