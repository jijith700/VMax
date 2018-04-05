package com.jijith.vmax.modules.home

import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * Created by jijith on 12/25/17.
 */

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}