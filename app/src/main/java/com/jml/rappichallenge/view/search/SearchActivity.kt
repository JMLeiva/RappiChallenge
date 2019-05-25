package com.jml.rappichallenge.view.search

import android.os.Bundle
import com.jml.rappichallenge.R
import com.jml.rappichallenge.view.base.BaseActivity
import kotlinx.android.synthetic.main.base_toolbar_activity.*

/*
 *	Activity responsible for allowing the user to perform a search
 * */
class SearchActivity : BaseActivity() {
    override val contentViewId: Int
        get() = R.layout.base_toolbar_activity

    private val FRAGMENT_TAG = "com.jml.SearchFragment"

    override val rootFragmentTags: Array<String>
        get() = arrayOf(FRAGMENT_TAG)

    override fun createFragment() {
        val fragment = SearchFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.search_screen_title)
    }
}
