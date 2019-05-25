package com.jml.rappichallenge.view.base

import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {

    protected abstract val rootFragmentTags: Array<String>
    protected abstract val contentViewId : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)

        // If activity is "recreating" (user rotated phone), fragment is restored by system
        if (savedInstanceState == null) {
            createFragment()
        } else {
            if (mustCreateFragment()) {
                createFragment()
            }
        }
    }

    protected abstract fun createFragment()

    private fun mustCreateFragment(): Boolean {
        for (tag in rootFragmentTags) {
            val found = supportFragmentManager.findFragmentByTag(tag)

            if(found != null) { return false }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
