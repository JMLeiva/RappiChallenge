package com.jml.rappichallenge.view.search

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.jmleiva.pagedrecyclerview.PagedViewHolder

import butterknife.ButterKnife
import com.jml.rappichallenge.GlideApp
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.enums.PictureSize
import com.jml.rappichallenge.models.tools.DateHelper
import com.jml.rappichallenge.tools.PictureUrlBuilder
import com.jml.rappichallenge.tools.VoteViewHelper

class SearchItemViewHolder internal constructor(itemView: View, callback: (View, Int) -> Unit) : PagedViewHolder(itemView), View.OnClickListener  {

    var iv_cover : ImageView
    var tv_title : TextView
    var pb_vote : ProgressBar
    var tv_vote_value : TextView
    var tv_releaseDate : TextView
    var tv_description : TextView

    val callback : (View, Int) -> Unit

    init {
        ButterKnife.bind(this, itemView)
        this.callback = callback
        itemView.setOnClickListener(this)
        iv_cover = itemView.findViewById(R.id.iv_cover)
        tv_title = itemView.findViewById(R.id.tv_title)
        pb_vote = itemView.findViewById(R.id.pb_vote)
        tv_vote_value = itemView.findViewById(R.id.tv_vote_value)
        tv_releaseDate = itemView.findViewById(R.id.tv_releaseDate)
        tv_description = itemView.findViewById(R.id.tv_description)

    }


    fun setup(context: Context, item: Movie) {
        tv_title.text = item.title

        VoteViewHelper.setupVoteViewForMovie(item, pb_vote, tv_vote_value, context)

        if (item.releaseDate != null) {
            tv_releaseDate.text = DateHelper.formatDate(item.releaseDate!!)
        } else {
            tv_releaseDate.visibility = View.INVISIBLE
        }
        tv_description.text = item.overview

        GlideApp.with(context)
                .load(PictureUrlBuilder.buildPosterUrl(item.posterPath, PictureSize.Poster.w185))
                .centerCrop()
                .placeholder(R.drawable.ic_photo_96dp)
                .error(R.drawable.ic_broken_image_96dp)
                .into(iv_cover)
    }

    override fun onClick(v: View) {
        callback(v, this.adapterPosition)
    }
}
