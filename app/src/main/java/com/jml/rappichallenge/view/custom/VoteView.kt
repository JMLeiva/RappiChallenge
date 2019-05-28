package com.jml.rappichallenge.view.custom

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.jml.rappichallenge.R
import com.jml.rappichallenge.tools.ViewTools


class VoteView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private lateinit var progressBar : ProgressBar
    private lateinit var voteTextView : TextView
    private lateinit var hyphenationTextView: TextView

    private var _progress : Int = 0



    init {

        val constraintSet = ConstraintSet()
        buildProgressBar(constraintSet)
        val linearLayout = buildLinearLayout(constraintSet)
        buildVoteTextView(linearLayout)
        buildHyphenationTextView(linearLayout)
        constraintSet.applyTo(this)

        background = ContextCompat.getDrawable(context!!, R.drawable.black_circle_background)
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.VoteView, 0, 0)
        _progress = typedArray.getInt(R.styleable.VoteView_progress, -1)
        typedArray.recycle()
    }


    private fun buildProgressBar(constraintSet: ConstraintSet){
        progressBar = ProgressBar(context, null,R.style.CircularDeterminateProgressBar)
        progressBar.id = View.generateViewId()
        addView(progressBar)
        progressBar.rotation = 270f
        progressBar.progressDrawable = ContextCompat.getDrawable(context!!, R.drawable.determinate_circleprogress)
        constraintSet.connect(progressBar.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(progressBar.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(progressBar.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(progressBar.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainWidth(progressBar.id, ViewTools.pxToDp(44, context))
        constraintSet.constrainHeight(progressBar.id, ViewTools.pxToDp(44, context))
        constraintSet.applyTo(this)
    }

    private fun buildLinearLayout(constraintSet: ConstraintSet) : LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.id = View.generateViewId()
        addView(linearLayout)
        constraintSet.connect(linearLayout.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(linearLayout.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(linearLayout.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(linearLayout.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.constrainWidth(linearLayout.id, LayoutParams.WRAP_CONTENT)
        constraintSet.constrainHeight(linearLayout.id, LayoutParams.WRAP_CONTENT)
        constraintSet.applyTo(this)

        return linearLayout
    }

    private fun buildVoteTextView(linearLayout: LinearLayout) {
        voteTextView = TextView(context)
        voteTextView.id = View.generateViewId()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            voteTextView.setTextAppearance(R.style.Text_Caption)
        } else {
            voteTextView.setTextAppearance(context, R.style.Text_Caption)
        }

        voteTextView.setTextColor(ContextCompat.getColor(context!!, R.color.white))
        val llVoteTextView = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        llVoteTextView.gravity = Gravity.CENTER
        linearLayout.addView(voteTextView, llVoteTextView)
        voteTextView.requestLayout()

    }

    private fun buildHyphenationTextView(linearLayout: LinearLayout) {
        hyphenationTextView = TextView(context)
        hyphenationTextView.id = View.generateViewId()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hyphenationTextView.setTextAppearance(R.style.Text_Tiny)
        } else {
            hyphenationTextView.setTextAppearance(context, R.style.Text_Tiny)
        }

        hyphenationTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
        hyphenationTextView.text = context.getString(R.string.vote_hyphenation)
        val llHyphenationTextView = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        llHyphenationTextView.gravity = Gravity.TOP
        linearLayout.addView(hyphenationTextView)
        hyphenationTextView.requestLayout()
    }

    private fun updateProgress() {
        if(_progress in 0..100) {
            setVoteValue()
        } else {
            setVoteNoValue()
        }
    }

    private fun setVoteValue() {
        progressBar.progress = _progress
        progressBar.progressDrawable.setColorFilter(getColorForValue(context, _progress / 100.0f), PorterDuff.Mode.SRC_IN)
        voteTextView.text = (_progress / 10f).toString()
        hyphenationTextView.visibility = View.VISIBLE
    }

    private fun setVoteNoValue() {
        progressBar.progress = 100
        progressBar.progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.rating_color_disabled), PorterDuff.Mode.SRC_IN)
        voteTextView.text = context.getString(R.string.no_vote)
        hyphenationTextView.visibility = View.GONE
    }

    private fun getColorForValue(context: Context, value : Float) : Int {
        val startColor = ContextCompat.getColor(context, R.color.rating_color_start)
        val endColor = ContextCompat.getColor(context, R.color.rating_color_end)


        return ArgbEvaluator().evaluate(value, startColor, endColor) as Int
    }

    fun setProgress(progress : Int, animate : Boolean) {

        if (animate && progress in 0..100) {

            val valueAnimator = ValueAnimator.ofInt(0, progress)
            valueAnimator.addUpdateListener {

                _progress = it.animatedValue as Int
                updateProgress()
            }

            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = 1000
            valueAnimator.start()
        }
        else {
            _progress = progress
            updateProgress()
        }
    }
}