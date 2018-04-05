package com.jijith.vmax.modules.add_product

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
abstract class AddProductActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideAddStockPresenter(addProductActivity: AddProductActivity, addProductView: AddProductView,
                                     apiService: ApiService, appDatabase : AppDatabase): AddProductPresenter {
            return AddProductPresenterImpl(addProductActivity, addProductView, apiService, appDatabase)
        }
    }

    @Binds
    abstract fun provideAddStockView(addProductActivity: AddProductActivity): AddProductView

}