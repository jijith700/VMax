package com.jijith.vmax.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by jijith on 12/26/17.
 */
abstract class BaseFragment : Fragment() {

    var mUnbinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        mUnbinder = ButterKnife.bind(this, view)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        return view
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onDestroyView() {
        super.onDestroyView()
        mUnbinder?.unbind()
    }
}