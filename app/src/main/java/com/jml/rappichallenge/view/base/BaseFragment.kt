package com.jml.rappichallenge.view.base

import android.view.View
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    //@Inject
    //protected var connectionManager: ConnectionManager? = null

    internal abstract fun getNoConnectionView() : View?

    protected fun setupUI() {
        getNoConnectionView()?.setOnClickListener { onRetryNoConnection() }
    }

    protected abstract fun onRetryNoConnection()

    protected fun showNoConnection() {
        getNoConnectionView()?.visibility = View.VISIBLE
    }

    protected fun hideNoConnection() {
        getNoConnectionView()?.visibility = View.GONE
    }
}
