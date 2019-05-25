package com.jml.rappichallenge.view.search

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import butterknife.BindView

import com.jmleiva.pagedrecyclerview.PagedViewHolder

import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.jml.rappichallenge.GlideApp
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.enums.PictureSize
import com.jml.rappichallenge.models.tools.DateHelper
import com.jml.rappichallenge.tools.PictureUrlBuilder

class SearchItemViewHolder internal constructor(itemView: View, callback: (View, Int) -> Void) : PagedViewHolder(itemView), View.OnClickListener  {

    var iv_cover : ImageView
    var tv_title : TextView
    var pb_vote : ProgressBar
    var tv_vote_value : TextView
    var tv_releaseDate : TextView
    var tv_description : TextView

    val callback : (View, Int) -> Void

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

        if(item.voteAverage != null && item.voteCount > 0) {
           setVoteValue(context, item.voteAverage!!)
        } else {
            setVoteNoValue(context)
        }

        if (item.releaseDate != null) {
            tv_releaseDate.text = DateHelper.formatDate(item.releaseDate!!)
        } else {
            tv_releaseDate.visibility = View.INVISIBLE
        }
        tv_description.text = item.overview

        GlideApp.with(context)
                .load(PictureUrlBuilder.buildPosterUrl(item.posterPath, PictureSize.Poster.w185))
                .centerCrop()
                //.placeholder(R.drawable.ic_photo_96dp)
                //.error(R.drawable.ic_broken_image_96dp)
                .into(iv_cover)
    }

    override fun onClick(v: View) {
        callback(v, this.adapterPosition)
    }

    private fun setVoteValue(context: Context, value : Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pb_vote.setProgress( (value * 10).toInt(), true)
        } else {
            pb_vote.progress = (value * 10).toInt()
        }
        pb_vote.progressDrawable.setColorFilter(getColorForValue(context, value / 10.0f), PorterDuff.Mode.SRC_IN)
        tv_vote_value.text = (value * 10).toInt().toString()
    }

    private fun setVoteNoValue(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pb_vote.setProgress(100, true)
        } else {
            pb_vote.progress = 100
        }
        pb_vote.progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.rating_color_disabled), PorterDuff.Mode.SRC_IN)
        tv_vote_value.text = context.getString(R.string.no_vote)
    }



    private fun getColorForValue(context: Context, value : Float) : Int {
        val startColor = ContextCompat.getColor(context, R.color.rating_color_start)
        val endColor = ContextCompat.getColor(context, R.color.rating_color_end)


        return ArgbEvaluator().evaluate(value, startColor, endColor) as Int
    }
}
