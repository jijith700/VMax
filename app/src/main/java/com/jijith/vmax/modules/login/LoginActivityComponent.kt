package com.jijith.vmax.modules.login

import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * Created by jijith on 12/25/17.
 */

@Subcomponent(modules = arrayOf(LoginActivityModule::class))
interface LoginActivityComponent : AndroidInjector<LoginActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<LoginActivity>()
}