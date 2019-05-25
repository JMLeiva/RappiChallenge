package com.jml.rappichallenge.view.search

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.other.SearchQuery
import com.jml.rappichallenge.repository.ErrorWrapper
import com.jml.rappichallenge.view.base.BaseFragment
import com.jml.rappichallenge.viewmodel.common.EntityListState
import com.jml.rappichallenge.viewmodel.search.SearchViewModel
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : BaseFragment(), PagedRecyclerViewAdapter.Paginator {

    private val SAVE_STATE_QUERY_STR = "jml.rappichallenge.SAVE_STATE_QUERY_STR"

    private var savedInstanceSearchQuery: SearchQuery? = null
    private lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    internal lateinit var adapter : SearchAdapter

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        setupObserver(searchViewModel)
        searchViewModel.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun setupUI() {
        super.setupUI()

        adapter = SearchAdapter(context!!) { movie -> onItemClick(movie) }
        adapter.paginator = this
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(context)
    }


    private fun setupObserver(viewModel: SearchViewModel) {

        viewModel.errorObservable.observe(this, Observer<ErrorWrapper> { errorWrapper ->
            if (connectionManager.isInternetConnected) {
                val errorMsg = errorWrapper?.message

                if (errorMsg != null) {
                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        })



        searchViewModel.entityListStateMutableLiveData.observe(this, Observer<EntityListState> { entityListState ->
            swiperefresh.isRefreshing = false

            adapter.stopLoading()

            when (entityListState) {
                EntityListState.Successful -> {
                    if (searchViewModel.data != null) {
                        adapter.appendItems(searchViewModel.data!!.result)
                    }
                    showResults()
                }
                EntityListState.NoConnection -> showNoConnection()
                EntityListState.NoResults -> showEmtpyState()
            }
        })
    }



    override fun hasMoreData(): Boolean {
        val result = searchViewModel.searchResponse
        return result?.isFinished ?: false
    }

    override fun loadNewPage() {
        searchViewModel.advance()
    }

    private fun showEmtpyState() {
        /*include_empty.setVisibility(View.VISIBLE)
        swiperefresh.visibility = View.GONE
        include_welcome.setVisibility(View.GONE)
        activity.invalidateOptionsMenu()
        hideNoConnection()*/
    }

    private fun showResults() {
        //include_welcome.setVisibility(View.GONE)
       // include_empty.setVisibility(View.GONE)
        swiperefresh.visibility = View.VISIBLE
        hideNoConnection()
        //activity.invalidateOptionsMenu()
    }


    override fun onRetryNoConnection() {
        TODO("not implemented")
    }

    private fun onItemClick(movie : Movie): Void {
        TODO("not implemented")
    }
}