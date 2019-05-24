package com.jml.rappichallenge.view.discover

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.view.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.discover_fragment.*

class DiscoverFragment : BaseFragment() {



    internal val SAVE_STATE_QUERY_STR = "jml.rappichallenge.SAVE_STATE_QUERY_STR"

    internal var savedInstanceSearchQuery: SearchQuery? = null

    override fun getNoConnectionView() : View? {
        return  include_no_connection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
            val searchQuery = savedInstanceState.getParcelable<Parcelable>(SAVE_STATE_QUERY_STR)
            if (searchQuery is SearchQuery) {
                savedInstanceSearchQuery = searchQuery
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.discover_fragment, container, false)

        setupUI()

        return view
    }

    /*override fun setupUI() {
        super.setupUI()
    }*/

    override fun onRetryNoConnection() {
        TODO("not implemented")
    }
}