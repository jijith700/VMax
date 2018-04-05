package com.jijith.vmax.base

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDex
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by jijith on 12/26/17.
 */
class Vmax : Application(), HasActivityInjector {

    @Inject lateinit
    var dispatchingAndroidInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

                DaggerAppComponent
                        .builder()
                        .application(this)
                        .build()
                        .inject(this);

//        Realm.init(this)

    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}