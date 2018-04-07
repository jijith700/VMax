package com.jijith.vmax.modules.home.all_products

import android.content.Context
import com.jijith.vmax.modules.home.MainActivity
import com.jijith.vmax.modules.home.MainView
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
class AllProductModule {

    @Provides
    fun provideAllProductFragment(allProductFragment: AllProductFragment) : AllProductView {
        return allProductFragment
    }

//    @Provides
//    fun provideMainView(mainActivity: MainActivity) : MainView {
//        return mainActivity
//    }

//    @Provides
//    fun provideContext(context: Context) : Context{
//        return context
//    }

}