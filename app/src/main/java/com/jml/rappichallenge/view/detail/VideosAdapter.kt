package com.jml.rappichallenge.view.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Video

class VideosAdapter(val context : Context, private var callback: (Video) -> Unit) : RecyclerView.Adapter<VideoViewHolder>() {

    var _videos : List<Video> = ArrayList()

    fun setVideos(videos : List<Video>) {
        this._videos = videos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view) { view, position ->
            callback(_videos[position])
        }
    }

    override fun getItemCount(): Int {
        return _videos.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.setup(_videos[position])
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        holder.release()
    }
}