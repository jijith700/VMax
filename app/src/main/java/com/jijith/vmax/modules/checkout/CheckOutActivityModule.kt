package com.jijith.vmax.modules.checkout

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
abstract class CheckOutActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideCheckOutPresenter(checkOutActivity: CheckOutActivity, checkOutView: CheckOutView,
                                     apiService: ApiService, appDatabase: AppDatabase): CheckOutPresenter {
            return CheckOutPresenterImpl(checkOutActivity, checkOutView, apiService, appDatabase)
        }
    }

    @Binds
    abstract fun provideCheckOutView(addProductActivity: CheckOutActivity): CheckOutView

}