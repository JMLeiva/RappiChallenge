package com.jml.rappichallenge.view.custom

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.jml.rappichallenge.R
import com.jml.rappichallenge.tools.ViewTools



class VoteView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    lateinit var progressBar : ProgressBar
    lateinit var voteTextView : TextView
    lateinit var hypenationTextView: TextView

    var _progress : Int = 0

    var progress : Int
        get() = _progress
        set(value) {
            _progress = value
            updateProgress()
        }

    init {

        val constraintSet = ConstraintSet()
        buildProgressBar(constraintSet)
        buildTextContainer(constraintSet)
        background = ContextCompat.getDrawable(context!!, R.drawable.black_circle_background)
        constraintSet.applyTo(this)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.VoteView, 0, 0)
        progress = typedArray.getInt(R.styleable.VoteView_progress, -1)
        typedArray.recycle()
    }

    private fun buildProgressBar(constraintSet: ConstraintSet){
        progressBar = ProgressBar(context, null, android.R.style.Widget_ProgressBar_Horizontal)
        progressBar.id = View.generateViewId()
        val ll_progressBar = LayoutParams(ViewTools.pxToDp(44f, context!!).toInt(), ViewTools.pxToDp(44f, context!!).toInt())
        addView(progressBar, ll_progressBar)

        progressBar.rotation = 270f
        progressBar.progressDrawable = ContextCompat.getDrawable(context, R.drawable.determinate_circleprogress)
        constraintSet.connect(progressBar.id, ConstraintSet.START, this.id, ConstraintSet.START)
        constraintSet.connect(progressBar.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP)
        constraintSet.connect(progressBar.id, ConstraintSet.END, this.id, ConstraintSet.END)
        constraintSet.connect(progressBar.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM)
    }

    private fun buildTextContainer(constraintSet: ConstraintSet) {
        val linearLayout = LinearLayout(context)
        linearLayout.id = View.generateViewId()
        val ll_linearLayout = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        addView(linearLayout, ll_linearLayout)
        constraintSet.connect(linearLayout.id, ConstraintSet.START, this.id, ConstraintSet.START)
        constraintSet.connect(linearLayout.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP)
        constraintSet.connect(linearLayout.id, ConstraintSet.END, this.id, ConstraintSet.END)
        constraintSet.connect(linearLayout.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM)

        voteTextView = TextView(context, null, R.style.Text_Caption)
        val ll_voteTextView = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        ll_voteTextView.gravity = Gravity.CENTER
        voteTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
        linearLayout.addView(voteTextView, ll_voteTextView)

        hypenationTextView = TextView(context, null, R.style.Text_Tiny)
        val ll_hypenationTextView = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        ll_hypenationTextView.gravity = Gravity.TOP
        hypenationTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
        linearLayout.addView(hypenationTextView, ll_hypenationTextView)
        hypenationTextView.text = context.getString(R.string.vote_hypenation)
    }


    private fun updateProgress() {
        if(_progress >= 0) {
            setVoteValue()
        } else {
            setVoteNoValue()
        }
    }

    private fun setVoteValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress( _progress, true)
        } else {
            progressBar.progress = _progress
        }
        progressBar.progressDrawable.setColorFilter(getColorForValue(context, _progress / 100.0f), PorterDuff.Mode.SRC_IN)
        voteTextView.text = (progress / 10f).toString()
    }

    private fun setVoteNoValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(100, true)
        } else {
            progressBar.progress = 100
        }
        progressBar.progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.rating_color_disabled), PorterDuff.Mode.SRC_IN)
        voteTextView.text = context.getString(R.string.no_vote)
    }

    private fun getColorForValue(context: Context, value : Float) : Int {
        val startColor = ContextCompat.getColor(context, R.color.rating_color_start)
        val endColor = ContextCompat.getColor(context, R.color.rating_color_end)


        return ArgbEvaluator().evaluate(value, startColor, endColor) as Int
    }
}