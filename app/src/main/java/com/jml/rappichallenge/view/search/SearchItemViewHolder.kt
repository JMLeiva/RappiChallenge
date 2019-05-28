package com.jml.rappichallenge.view.search

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.jmleiva.pagedrecyclerview.PagedViewHolder

import com.jml.rappichallenge.GlideApp
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.enums.PictureSize
import com.jml.rappichallenge.models.tools.DateHelper
import com.jml.rappichallenge.tools.PictureUrlBuilder
import com.jml.rappichallenge.view.custom.VoteView

class SearchItemViewHolder internal constructor(itemView: View, val callback: (View, Int) -> Unit) : PagedViewHolder(itemView), View.OnClickListener  {

    private var ivCover : ImageView
    private var tvTitle : TextView
    private var flVoteContainer : VoteView
    private var tvReleaseDate : TextView
    private var tvDescription : TextView

    init {
        itemView.setOnClickListener(this)
        ivCover = itemView.findViewById(R.id.iv_cover)
        tvTitle = itemView.findViewById(R.id.tv_title)
        flVoteContainer = itemView.findViewById(R.id.fl_vote_container)
        tvReleaseDate = itemView.findViewById(R.id.tv_releaseDate)
        tvDescription = itemView.findViewById(R.id.tv_description)

    }


    fun setup(context: Context, item: Movie, animate : Boolean) {
        tvTitle.text = item.title

        if(item.voteCount > 0 && item.voteAverage != null) {
            flVoteContainer.setProgress((item.voteAverage!! * 10f).toInt(), animate)
        } else {
            flVoteContainer.setProgress(-1, false)
        }

        if (item.releaseDate != null) {
            tvReleaseDate.text = DateHelper.formatDate(item.releaseDate!!)
        } else {
            tvReleaseDate.visibility = View.INVISIBLE
        }
        tvDescription.text = item.overview

        GlideApp.with(context)
                .load(PictureUrlBuilder.buildPosterUrl(item.posterPath, PictureSize.Poster.w185))
                .centerCrop()
                .placeholder(R.drawable.ic_photo_96dp)
                .error(R.drawable.ic_broken_image_96dp)
                .into(ivCover)
    }

    override fun onClick(v: View) {
        callback(v, this.adapterPosition)
    }
}
