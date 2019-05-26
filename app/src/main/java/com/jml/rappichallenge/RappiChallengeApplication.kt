package com.jml.rappichallenge

import android.app.Activity
import android.app.Application
import com.jml.rappichallenge.di.DaggerRappiChallengeAppComponent
import com.jml.rappichallenge.di.RappiChallengeAppComponent
import com.jml.rappichallenge.tools.RealmConfigurator
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class RappiChallengeApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector : DispatchingAndroidInjector<Activity>
    lateinit var component: RappiChallengeAppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerRappiChallengeAppComponent.builder().application(this).build()
        component.inject(this)

        RealmConfigurator.configureWithContext(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityInjector
    }
}