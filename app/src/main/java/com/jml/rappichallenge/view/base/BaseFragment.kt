package com.jml.rappichallenge.view.base

import android.view.View
import com.jml.rappichallenge.tools.ConnectionManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var connectionManager: ConnectionManager

    internal abstract fun getNoConnectionView() : View?

    internal open fun setupUI() {
        getNoConnectionView()?.setOnClickListener {
            onRetryNoConnection()
        }
    }

    protected abstract fun onRetryNoConnection()

    internal open fun showNoConnection() {
        getNoConnectionView()?.visibility = View.VISIBLE
    }

    internal open fun hideNoConnection() {
        getNoConnectionView()?.visibility = View.GONE
    }
}
