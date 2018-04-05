package com.jijith.vmax.modules.home

import com.jijith.vmax.modules.home.recent.RecentFragment
import com.jijith.vmax.modules.home.recent.RecentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jijith on 1/25/18.
 */
@Module
abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = arrayOf(RecentModule::class))
    abstract fun provideRecentFragmentFactory(): RecentFragment
}