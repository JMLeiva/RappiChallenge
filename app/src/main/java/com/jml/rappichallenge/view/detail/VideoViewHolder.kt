package com.jml.rappichallenge.view.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.jml.rappichallenge.BuildConfig
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Video
import android.util.Log
import android.widget.FrameLayout


class VideoViewHolder(itemView: View, val callback: (View, Int) -> Unit) : RecyclerView.ViewHolder(itemView), View.OnClickListener {



    private var yv_thumbnail : YouTubeThumbnailView = itemView.findViewById(R.id.yv_thumbnail)
    private var loader : YouTubeThumbnailLoader? = null
    private var currentVideo : Video? = null
    private var mustPrepareLoaded : Boolean = true

    private var background : FrameLayout

    init {
        background = itemView.findViewById(R.id.fl_background)
        itemView.setOnClickListener(this)
        prepareLoader()
    }

    fun prepareLoader() {
        mustPrepareLoaded = false
        yv_thumbnail.initialize(BuildConfig.YouTubeApiKey, object : YouTubeThumbnailView.OnInitializedListener {

            override fun onInitializationSuccess(thumbnailView: YouTubeThumbnailView?, thumbnailLoader: YouTubeThumbnailLoader?) {
                loader = thumbnailLoader
                if (currentVideo != null) {
                    setThumbnail(loader!!, currentVideo!!.key)
                }
            }

            override fun onInitializationFailure(p0: YouTubeThumbnailView?, p1: YouTubeInitializationResult?) {
                Log.e("YOUTUBE", "Youtube Thumbnail Error")
            }
        })
    }

    fun setup(video: Video) {
        currentVideo = video

        if (loader != null) {
            setThumbnail(loader!!, video.key)
        } else if(mustPrepareLoaded) {
            prepareLoader()
        }
    }

    private fun setThumbnail(loader: YouTubeThumbnailLoader, key : String) {
        loader.setVideo(key)
    }

    override fun onClick(v: View?) {

        if (v == null) {
            return
        }

        callback(v, adapterPosition)
    }

    fun release() {
        loader?.release()
        loader = null

        mustPrepareLoaded = true
    }
}