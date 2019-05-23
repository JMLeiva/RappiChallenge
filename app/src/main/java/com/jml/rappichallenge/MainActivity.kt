package com.jml.rappichallenge

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log

import com.jml.rappichallenge.models.entities.MovieDiscoverResponse
import com.jml.rappichallenge.models.enums.Language
import com.jml.rappichallenge.models.enums.Sorting
import com.jml.rappichallenge.repository.api.ApiCallback
import com.jml.rappichallenge.repository.api.MdbApiImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Test
        val api = MdbApiImpl()

        api.discoverMovie(Language.English, Sorting.Popularity, 1, null, object : ApiCallback<MovieDiscoverResponse> {
            override fun onResponse(response: MovieDiscoverResponse) {
                Log.i("TEST", response.toString())
            }

            override fun onFailure(code: Int, message: String?) {
                Log.i("TEST", message)
            }
        })
    }
}
