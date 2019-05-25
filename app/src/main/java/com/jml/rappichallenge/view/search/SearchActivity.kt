package com.jml.rappichallenge.view.search

import android.os.Bundle
import com.jml.rappichallenge.R
import com.jml.rappichallenge.view.base.BaseActivity

/*
 *	Activity responsible for allowing the user to perform a search
 * */
class SearchActivity : BaseActivity() {
    private val FRAGMENT_TAG = "com.jml.SearchActivity"

    override val rootFragmentTags: Array<String>
        get() = arrayOf(FRAGMENT_TAG)

    override fun createFragment() {
        val fragment = SearchFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.search_screen_title)
    }
}
