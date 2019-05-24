package com.jml.rappichallenge.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View.GONE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.jml.rappichallenge.R
import com.jml.rappichallenge.view.discover.DiscoverActivity
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {


    var appearAnimation: Animation? = null
    var disppearAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        fl_conatainer.setOnClickListener {
            skipAnimations()
        }

        appearAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_appear)
        disppearAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_disappear)

        startAppearAnimation()
    }

    private fun startAppearAnimation(){

        appearAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                startDisappearAnimation()
            }

            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        rl_splash_content_container.startAnimation(appearAnimation)
    }

    private fun startDisappearAnimation()
    {
        disppearAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                goToMainScreen()
            }

            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}

        })

        rl_splash_content_container.startAnimation(disppearAnimation)
    }

    private fun  skipAnimations(){
        appearAnimation!!.setAnimationListener(null)
        disppearAnimation!!.setAnimationListener(null)

        rl_splash_content_container.animation?.cancel()
        goToMainScreen()
    }

    private fun goToMainScreen(){
        rl_splash_content_container.visibility = GONE

        val intent = Intent(this, DiscoverActivity::class.java)
        startActivity(intent)
        finish()
    }
}
