package com.jml.rappichallenge.view.detail

import android.os.Bundle
import com.jml.rappichallenge.R
import com.jml.rappichallenge.view.base.BaseActivity

/*
 *	Activity responsible to show a movie detail
 * */
class DetailActivity : BaseActivity() {

    companion object Extras {
        val movieIdExtra = "com.jml.MovieIdExtra"
    }

    private val FRAGMENT_TAG = "com.jml.DetailFragment"

    override val contentViewId: Int
        get() = R.layout.base_no_toolbar_activity

    override val rootFragmentTags: Array<String>
        get() = arrayOf(FRAGMENT_TAG)

    override fun createFragment() {
        val fragment = DetailFragment()
        val args = Bundle()
        args.putInt(DetailFragment.movieIdExtra, intent.getIntExtra(movieIdExtra, -1))
        fragment.arguments = args
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit()
    }
}
