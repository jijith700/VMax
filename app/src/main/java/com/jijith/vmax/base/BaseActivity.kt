package com.jijith.vmax.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import butterknife.ButterKnife
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created by jijith on 12/25/17.
 */
abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

//    @Inject
//    var fragmentManager: FragmentManager? = null

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        ButterKnife.bind(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        AndroidInjection.inject(this)
    }


    /**
     * get layout to inflate
     */
    @LayoutRes
    abstract fun getLayout(): Int


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

//    protected fun addFragment(@IdRes containerViewId: Int, fragment: Fragment) {
//        fragmentManager?.beginTransaction()
//                ?.add(containerViewId, fragment)
//                ?.commit()
//    }
}