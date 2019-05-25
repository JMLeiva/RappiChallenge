package com.jml.rappichallenge.view.search

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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

    internal var notConnectionSnackbar : Snackbar? = null

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

        swiperefresh.setOnRefreshListener {
            adapter.clear()
            searchViewModel.resetPaging()
        }

        showResults()
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
            showResults()
            adapter.stopLoading()

            when (entityListState) {
                EntityListState.Successful -> {
                    if (searchViewModel.data != null) {
                        adapter.appendItems(searchViewModel.data!!.result)
                    }
                }
                EntityListState.NoConnection -> showNoConnection()
                EntityListState.NoResults -> showEmtpyState()
            }
        })
    }

    override fun hasMoreData(): Boolean {

        // If swipe refreshing, return false
        if (swiperefresh.isRefreshing) { return false }
        if (searchViewModel.entityListStateMutableLiveData.value == EntityListState.NoConnection &&
                !connectionManager.isInternetConnected) { return false }

        val result = searchViewModel.searchResponse
        val finished = result?.isFinished ?: false
        return !finished
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
        rv_list.visibility = View.VISIBLE
        swiperefresh.visibility = View.VISIBLE
        hideNoConnection()
        //activity.invalidateOptionsMenu()
    }

    private fun hideResults() {
        swiperefresh.visibility = View.GONE
    }

    override fun showNoConnection() {

        if(adapter.itemCount > 0){
            notConnectionSnackbar = Snackbar.make(fl_conatainer, R.string.search_noConnectionTextTitle, Snackbar.LENGTH_INDEFINITE)
            notConnectionSnackbar?.setAction(R.string.retry) { onRetryNoConnection() }
            notConnectionSnackbar!!.show()
            return
        }

        super.showNoConnection()
        hideResults()
    }

    override fun hideNoConnection() {
        super.hideNoConnection()
        notConnectionSnackbar?.dismiss()
    }

    override fun onRetryNoConnection() {
        showResults()
        searchViewModel.start()
    }

    private fun onItemClick(movie : Movie) {
      Log.i("TODO", "TODO")
    }
}