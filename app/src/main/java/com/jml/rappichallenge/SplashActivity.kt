package com.jml.rappichallenge

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View.GONE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        startAppearAnimation()
    }

    private fun startAppearAnimation(){

        val appearAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_appear)
        rl_splash_content_container.startAnimation(appearAnimation)

        appearAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                startDisappearAnimation()
            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
    }

    private fun startDisappearAnimation()
    {
        val disppearAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_disappear)
        rl_splash_content_container.startAnimation(disppearAnimation)

        disppearAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                goToMainScreen()
            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
    }

    private fun goToMainScreen(){
        rl_splash_content_container.visibility = GONE

    }
}
