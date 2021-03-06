package com.jml.rappichallenge.view.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
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
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import com.jml.rappichallenge.tools.Debouncer
import com.jml.rappichallenge.view.detail.DetailActivity


class SearchFragment : BaseFragment(), PagedRecyclerViewAdapter.Paginator, SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    private companion object SaveStateKey {
        val stListPosition = "jml.rappichallenge.searchFragment.listPosition"
        val stSearchText = "jml.rappichallenge.searchFragment.searchText"
    }


    private lateinit var searchViewModel: SearchViewModel

    internal var notConnectionSnackbar : Snackbar? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val searchDebouncer = Debouncer(300)

    private lateinit var adapter : SearchAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager
    private var pendingScrollToPosition : Int? = null
    private var searchText : String? = null

    internal var searchView: SearchView? = null



    override fun getNoConnectionView() : View? {
        return  include_no_connection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
            pendingScrollToPosition = savedInstanceState.getInt(stListPosition)
            searchText = savedInstanceState.getString(stSearchText)
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
        adapter.filterByText(searchText)

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

        viewModel.entityListStateMutableLiveData.observe(this, Observer<EntityListState> { entityListState ->
            swiperefresh.isRefreshing = false
            showResults()


            when (entityListState) {
                EntityListState.Successful -> {
                    setupSortingText()
                    if (searchViewModel.data != null) {
                        refreshListWithResults()
                    }
                }
                EntityListState.NoConnection -> {
                    showNoConnection()
                    adapter.stopLoading()
                }
                EntityListState.NoResults -> {
                    showEmtpyState()
                    adapter.stopLoading()
                }
            }
        })
    }

    private fun refreshListWithResults() {
        if( adapter.setItems(searchViewModel.data!!) ) {

            if (pendingScrollToPosition != null) {
                rv_list.scrollToPosition(pendingScrollToPosition!!)
                pendingScrollToPosition = null
            }

            adapter.stopLoading()
        }
        else {
            // Delay request a bit to avoid quota excess (4 / second max)
            Handler().postDelayed(  {
                searchViewModel.advance()
            }, 350)

        }
    }

    override fun hasMoreData(): Boolean {

        // If swipe refreshing, return false
        if (swiperefresh.isRefreshing) { return false }
        if (searchViewModel.entityListStateMutableLiveData.value == EntityListState.NoConnection &&
                !connectionManager.isInternetConnected) { return false }

        val result = searchViewModel.data
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
        rv_list.visibility = View.VISIBLE
        ll_resultsContainer.visibility = View.VISIBLE
        hideNoConnection()
    }

    private fun hideResults() {
        ll_resultsContainer.visibility = View.GONE
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
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.movieIdExtra, movie.id)
        startActivity(intent)
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        outState.putInt(stListPosition, linearLayoutManager.findFirstVisibleItemPosition())
        outState.putString(stSearchText, searchText)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if(context == null) { return }

        inflater.inflate(R.menu.search_menu, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)

        val searchManager = context!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager


        // Setup SearchView
        searchView = searchMenuItem.actionView as SearchView

        searchView?.queryHint = context!!.getString(R.string.search_hint)
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView?.isSubmitButtonEnabled = false
        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener(this)

        if(searchText != null) {
            val copiedSearchText = searchText
            searchMenuItem.expandActionView()
            searchView?.post {
                searchView?.setQuery(copiedSearchText, true)
                searchView?.clearFocus()
            }

        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) {
            return false
        }

        searchDebouncer.push {
            adapter.filterByText(query)
            showResults()
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null) {
            return false
        }

        searchText = newText

        searchDebouncer.push {
            adapter.filterByText(newText)
            showResults()
        }
        return true
    }

    override fun onClose(): Boolean {
        return true
    }
}