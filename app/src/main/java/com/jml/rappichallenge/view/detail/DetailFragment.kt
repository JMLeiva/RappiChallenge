package com.jml.rappichallenge.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jml.rappichallenge.GlideApp
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.entities.Namable
import com.jml.rappichallenge.models.enums.PictureSize
import com.jml.rappichallenge.models.enums.RequestState
import com.jml.rappichallenge.models.tools.DateHelper
import com.jml.rappichallenge.tools.PictureUrlBuilder
import com.jml.rappichallenge.tools.VoteViewHelper
import com.jml.rappichallenge.view.base.BaseFragment
import com.jml.rappichallenge.viewmodel.common.EntityState
import com.jml.rappichallenge.viewmodel.detail.MovieViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.vote_layout.*
import javax.inject.Inject

class DetailFragment : BaseFragment() {

    companion object Extras {
        val movieIdExtra = "com.jml.MovieIdExtra"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var movieViewModel: MovieViewModel
    private var movieId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
           // TODO
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java)
        setupObserver(movieViewModel)

        movieId = arguments?.getInt(movieIdExtra)
        movieViewModel.setItemId(movieId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupObserver(viewModel: MovieViewModel) {

        viewModel.errorObservable.observe(this, Observer{ errorWrapper ->
            if (connectionManager.isInternetConnected) {
                val errorMsg = errorWrapper?.message

                if (errorMsg != null) {
                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.stateObservable.observe(this, Observer {state ->
            when(state) {
                RequestState.Loading -> showLoading()
                RequestState.Failure, RequestState.Success -> hideLoading()
            }
        })

        viewModel.entityStateObserbale.observe(this, Observer{ entityState ->

            when (entityState) {
                EntityState.Successful -> {
                    showResult(viewModel.data!!)
                }
                EntityState.NoConnection -> showNoConnection()
            }
        })
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showResult(movie : Movie) {

        if(context == null) {
            showNotFoundDialog()
            return
        }

        setupMainInfo(movie)
        setupGenres(movie)
    }

    private fun setupMainInfo(movie : Movie) {
        GlideApp.with(context!!)
                .load(PictureUrlBuilder.buildBackdropUrl(movie.backdropPath, PictureSize.Backdrop.w780))
                .centerCrop()
                .placeholder(R.drawable.ic_photo_96dp)
                .error(R.drawable.ic_broken_image_96dp)
                .into(iv_backdrop)

        GlideApp.with(context!!)
                .load(PictureUrlBuilder.buildPosterUrl(movie.posterPath, PictureSize.Poster.w300))
                .centerCrop()
                .placeholder(R.drawable.ic_photo_96dp)
                .error(R.drawable.ic_broken_image_96dp)
                .into(iv_cover)

        tv_title.text = movie.title
        tv_description.text = movie.overview
        if (movie.releaseDate != null) {
            tv_date.visibility = View.VISIBLE
            tv_date.text = DateHelper.formatDate(movie.releaseDate!!)
        } else {
            tv_date.visibility = View.INVISIBLE
        }


        VoteViewHelper.setupVoteViewForMovie(movie, pb_vote, tv_vote_value, context!!)
    }

    private fun setupGenres(movie : Movie) {

        if (movie.genres.isEmpty()) {
            ll_genresContainer.visibility = View.GONE
        } else {
            ll_genresContainer.visibility = View.VISIBLE
            fbl_genres.removeAllViews()
            for(genre : Namable in movie.genres) {
                fbl_genres.addView(buildGenreView(genre))
            }
        }
    }

    private fun buildGenreView(genre : Namable) : View {

        val view = LayoutInflater.from(context!!).inflate(R.layout.genre_item, fbl_genres, false) as TextView
        view.text = genre.name
        return view
    }

    private fun showNotFoundDialog() {
        // TODO
    }

    override fun onRetryNoConnection() {
       //TODO
    }

    override fun getNoConnectionView(): View? {
       return include_no_connection
    }

}