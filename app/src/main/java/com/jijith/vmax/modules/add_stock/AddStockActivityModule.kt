package com.jijith.vmax.modules.add_stock

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
 abstract class AddStockActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideAddStockPresenter(addStockActivity: AddStockActivity, addStockView: AddStockView,
                                     apiService: ApiService, appDatabase: AppDatabase): AddStockPresenter {
            return AddStockPresenterImpl(addStockActivity, addStockView, apiService, appDatabase)
        }
    }

    @Binds
    abstract fun provideAddStockView(addStockActivity: AddStockActivity) : AddStockView

}