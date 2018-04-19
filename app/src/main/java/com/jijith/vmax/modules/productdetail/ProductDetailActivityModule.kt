package com.jijith.vmax.modules.productdetail

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
abstract class ProductDetailActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideProductDetailPresenter(productDetailActivity: ProductDetailActivity, productDetailView: ProductDetailView,
                                          apiService: ApiService, appDatabase: AppDatabase): ProductDetailPresenter {
            return ProductDetailPresenterImpl(productDetailActivity, productDetailView, apiService, appDatabase)
        }
    }

    @Binds
    abstract fun provideProductDetailtView(productListActivity: ProductDetailActivity): ProductDetailView


}