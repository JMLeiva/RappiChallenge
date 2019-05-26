package com.jml.rappichallenge.view.youtube

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.youtube.player.*
import com.jml.rappichallenge.BuildConfig
import com.jml.rappichallenge.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class YouTubeFragment : YouTubePlayerSupportFragment(), HasSupportFragmentInjector, YouTubePlayer.OnInitializedListener {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    companion object Extras {
        val youtubeKeyExtra = "com.jml.YoutubeKeyExtra"
    }

    var youtubeKey : String? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        youtubeKey = arguments?.getString(youtubeKeyExtra)
        setupUI()
    }

    fun setupUI() {
        if (youtubeKey == null) {
            // TODO show error
            return
        }

        initialize(BuildConfig.YouTubeApiKey, this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        if (!wasRestored) {
            youtubePlayer?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youtubePlayer?.loadVideo(youtubeKey)

        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        // TODO show error
    }
}