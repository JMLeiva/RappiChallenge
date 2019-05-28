package com.jml.rappichallenge.tools

import android.content.Context
import android.util.TypedValue



object ViewTools {
    fun pxToDp(dp : Int, context: Context) : Int {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics).toInt()
    }
}