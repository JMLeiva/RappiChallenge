package com.jml.rappichallenge.tools

import android.content.Context
import android.util.TypedValue



object ViewTools {
    fun pxToDp(dp : Float, context: Context) : Float {
        val r = context.getResources()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics() )
    }
}