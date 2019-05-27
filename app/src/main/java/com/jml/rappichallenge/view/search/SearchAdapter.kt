package com.jml.rappichallenge.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jml.rappichallenge.R
import com.jml.rappichallenge.models.entities.Movie
import com.jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter
import com.jmleiva.pagedrecyclerview.PagedViewHolder

class SearchAdapter(private var context: Context, private var callback: (Movie) -> Unit) : PagedRecyclerViewAdapter<SearchItemViewHolder, PagedViewHolder>() {

    private var items: MutableList<Movie> = ArrayList()
    private var textFilter: String? = null

    private val filteredItems: List<Movie>
        get() = filtered(items)

    private fun filtered(list : List<Movie>) : List<Movie> {
        return if (textFilter?.isEmpty() == false) {
            list.filter { movie -> movie.title!!.contains(textFilter!!.toLowerCase(), true) }
        } else {
            list
        }
    }

    fun clear(){
        items.clear()
        notifyDataSetChanged()
    }

    // returns if seting this items has any effect (aka, wont be filtered)
    // this is a workaroud to avoid an ugly animation glitch when a new page is loaded and then filtered completely
    fun setItems(list: List<Movie>) : Boolean {

        val allFiltered = newListIsAllFiltered(list)

        this.items.clear()
        this.items.addAll(list)


        if(!allFiltered){
            notifyDataSetChanged()
            return true
        }

        return false
    }

    fun newListIsAllFiltered(list: List<Movie>) : Boolean {
        val diff = list.filter { itemA -> items.find { itemB -> itemA.id == itemB.id} == null }

        return filtered(diff).isEmpty()
    }

    override fun getPagedItemCount(): Int {
        return filteredItems.size
    }

    override fun onCreateNormalViewHolder(parent: ViewGroup?, viewType: Int): SearchItemViewHolder {

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.search_item_card, parent, false)
        return SearchItemViewHolder(view, onItemClick)
    }

    override fun onCreateLoadingViewHolder(parent: ViewGroup?, viewType: Int): PagedViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.loading_item, parent, false)
        return PagedViewHolder(view)
    }

    override fun onBindLoadingViewHolder(holder: PagedViewHolder?) {
        // Do nothing
    }

    override fun onBindNormalViewHolder(holder: SearchItemViewHolder?, position: Int) {
        holder?.setup(context, filteredItems[position])
    }

    private val onItemClick = { _: View , position: Int ->
        callback(filteredItems[position])
    }

    fun filterByText(query: String?) {

        if(this.textFilter?.equals(query) == true) { return }
        this.textFilter = query
        notifyDataSetChanged()
    }


}