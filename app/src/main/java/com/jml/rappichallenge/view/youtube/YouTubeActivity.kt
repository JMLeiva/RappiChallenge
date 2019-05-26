package com.jml.rappichallenge.view.youtube

import android.os.Bundle
import com.jml.rappichallenge.R
import com.jml.rappichallenge.view.base.BaseActivity

/*
 *	Activity responsible to show a movie detail
 * */
class YouTubeActivity : BaseActivity() {

    companion object Extras {
        val youtubeKeyExtra = "com.jml.YoutubeKeyExtra"
    }

    private val FRAGMENT_TAG = "com.jml.YouTubeFragment"

    override val contentViewId: Int
        get() = R.layout.base_no_toolbar_activity

    override val rootFragmentTags: Array<String>
        get() = arrayOf(FRAGMENT_TAG)

    override fun createFragment() {
        val fragment = YouTubeFragment()
        val args = Bundle()
        args.putString(YouTubeFragment.youtubeKeyExtra, intent.getStringExtra(youtubeKeyExtra))
        fragment.arguments = args
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit()
    }
}
