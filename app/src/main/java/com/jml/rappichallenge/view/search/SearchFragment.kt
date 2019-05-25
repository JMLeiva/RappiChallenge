package com.jml.rappichallenge.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie
import com.jml.rappichallenge.models.enums.Sorting
import com.jml.rappichallenge.repository.ErrorWrapper
import com.jml.rappichallenge.view.base.BaseFragment
import com.jml.rappichallenge.viewmodel.common.EntityListState
import com.jml.rappichallenge.viewmodel.search.SearchViewModel
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject
import androidx.appcompat.widget.PopupMenu


class SearchFragment : BaseFragment, PagedRecyclerViewAdapter.Paginator {

    private companion object SaveStateKey {
        val listPosition = "jml.rappichallenge.searchFragment.listPosition"
    }


    private lateinit var searchViewModel: SearchViewModel

    internal var notConnectionSnackbar : Snackbar? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter : SearchAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager
    private var pendingScrollToPosition : Int? = null

    constructor() : super() {
        Log.i("TEST", "TEST")
    }

    override fun getNoConnectionView() : View? {
        return  include_no_connection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
            pendingScrollToPosition = savedInstanceState.getInt(SaveStateKey.listPosition)
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
        linearLayoutManager = LinearLayoutManager(context)
        rv_list.adapter = adapter
        rv_list.layoutManager = linearLayoutManager

        swiperefresh.setOnRefreshListener {
            searchViewModel.resetPaging()
        }

        fl_sorting.setOnClickListener { openSortingOptions() }

        showResults()
    }

    private fun setupSortingText() {

        when(searchViewModel.sorting) {
            Sorting.Popularity -> tv_sorting.text = getString(R.string.sorting_popularity)
            Sorting.Date -> tv_sorting.text = getString(R.string.sorting_date)
            Sorting.Rating -> tv_sorting.text = getString(R.string.sorting_rating)
        }

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
                    setupSortingText()
                    if (searchViewModel.data != null) {
                        refreshListWithResults()
                    }
                }
                EntityListState.NoConnection -> showNoConnection()
                EntityListState.NoResults -> showEmtpyState()
            }
        })
    }

    private fun refreshListWithResults() {
        adapter.setItems(searchViewModel.data!!.result)

        if ( pendingScrollToPosition != null) {
            rv_list.scrollToPosition(pendingScrollToPosition!!)
            pendingScrollToPosition = null
        }
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

    private fun openSortingOptions() {
        if(context != null){
            val popup = PopupMenu(context!!, fl_sorting)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.sorting_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.action_sorting_popularity -> setSorting(Sorting.Popularity)
                    R.id.action_sorting_rating -> setSorting(Sorting.Rating)
                    R.id.action_sorting_date -> setSorting(Sorting.Date)
                    else -> { return@setOnMenuItemClickListener false }
                }

                return@setOnMenuItemClickListener true
            }
            popup.show()
        }
    }

    private fun setSorting(sorting : Sorting) {
        adapter.clear()
        searchViewModel.sorting = sorting
    }

    private fun onItemClick(movie : Movie) {
      Log.i("TODO", "TODO")
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        outState.putInt(SaveStateKey.listPosition, linearLayoutManager.findFirstVisibleItemPosition())
    }

}