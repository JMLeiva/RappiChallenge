package com.jml.rappichallenge.view.base

import android.os.Bundle
import android.view.MenuItem
import com.jml.rappichallenge.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.base_activity.*


abstract class BaseActivity : DaggerAppCompatActivity() {

    protected abstract val rootFragmentTags: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)

        setSupportActionBar(toolbar)

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
            supportFragmentManager.findFragmentByTag(tag) ?: return false
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
