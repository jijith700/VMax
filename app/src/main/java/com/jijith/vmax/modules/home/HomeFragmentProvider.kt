package com.jijith.vmax.modules.home

import com.jijith.vmax.modules.home.all_products.AllProductFragment
import com.jijith.vmax.modules.home.all_products.AllProductModule
import com.jijith.vmax.modules.home.recent.RecentFragment
import com.jijith.vmax.modules.home.recent.RecentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jijith on 1/25/18.
 */
@Module
abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = arrayOf(AllProductModule::class))
    abstract fun bindAllProductFragment(): AllProductFragment

    @ContributesAndroidInjector(modules = arrayOf(RecentModule::class))
    abstract fun bindRecentFragment(): RecentFragment
}