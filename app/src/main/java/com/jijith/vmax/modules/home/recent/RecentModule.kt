package com.jijith.vmax.modules.home.recent

import android.content.Context
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
class RecentModule {

    @Provides
    fun provideRecentFragment(recentFragment: RecentFragment) : RecentView {
        return recentFragment
    }

    @Provides
    fun provideContext(context: Context) : Context{
        return context
    }


//    @Provides
//    fun provideRecentView(recentView: RecentView) :RecentView {
//        return recentView
//    }

}