package com.jml.rappichallenge.view.discover

import com.jml.rappichallenge.R
import com.jml.rappichallenge.view.base.BaseActivity

/*
 *	Activity responsible for allowing the user to perform a search
 * */
class DiscoverActivity : BaseActivity() {
    private val FRAGMENT_TAG = "com.jml.DiscoverActivity"

    override val rootFragmentTags: Array<String>
        get() = arrayOf(FRAGMENT_TAG)

    override fun createFragment() {
        val fragment = DiscoverFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit()
    }
}
