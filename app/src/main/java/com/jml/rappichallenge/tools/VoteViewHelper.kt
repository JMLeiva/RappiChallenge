package com.jml.rappichallenge.tools

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie

object VoteViewHelper {

    fun setupVoteViewForMovie(movie : Movie, progressBar : ProgressBar, textView : TextView, context : Context) {
        if(movie.voteAverage != null && movie.voteCount > 0) {
            setVoteValue(progressBar, textView, context, movie.voteAverage!!)
        } else {
            setVoteNoValue(progressBar, textView, context)
        }
    }

    private fun setVoteValue(progressBar : ProgressBar, textView : TextView, context : Context, value : Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress( (value * 10).toInt(), true)
        } else {
            progressBar.progress = (value * 10).toInt()
        }
        progressBar.progressDrawable.setColorFilter(getColorForValue(context, value / 10.0f), PorterDuff.Mode.SRC_IN)
        textView.text = (value * 10).toInt().toString()
    }

    private fun setVoteNoValue(progressBar : ProgressBar, textView : TextView, context : Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(100, true)
        } else {
            progressBar.progress = 100
        }
        progressBar.progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.rating_color_disabled), PorterDuff.Mode.SRC_IN)
        textView.text = context.getString(R.string.no_vote)
    }

    private fun getColorForValue(context: Context, value : Float) : Int {
        val startColor = ContextCompat.getColor(context, R.color.rating_color_start)
        val endColor = ContextCompat.getColor(context, R.color.rating_color_end)


        return ArgbEvaluator().evaluate(value, startColor, endColor) as Int
    }
}