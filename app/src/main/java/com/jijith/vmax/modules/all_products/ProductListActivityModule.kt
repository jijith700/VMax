package com.jijith.vmax.modules.all_products

import android.content.Context
import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.modules.delete_product.DeleteProductView
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
 abstract class ProductListActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideDeleteProductPresenter(productListActivity: ProductListActivity, productListView: ProductListView,
                                          apiService: ApiService, appDatabase: AppDatabase): ProductListPresenter {
            return ProductListPresenterImpl(productListActivity, productListView, apiService, appDatabase)
        }

        @JvmStatic
        @Provides
        fun provideProductListAdapter(context: Context): ProductListAdapter {
            return ProductListAdapter(context)
        }
    }

    @Binds
    abstract fun provideProductListView(productListActivity: ProductListActivity) : ProductListView


}