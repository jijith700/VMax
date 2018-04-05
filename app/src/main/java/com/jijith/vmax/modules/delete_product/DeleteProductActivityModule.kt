package com.jijith.vmax.modules.delete_product

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
 abstract class DeleteProductActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideDeleteProductPresenter(deleteProductActivity: DeleteProductActivity, deleteProductView: DeleteProductView,
                                          apiService: ApiService, appDatabase: AppDatabase): DeleteProductPresenter {
            return DeleteProductPresenterImpl(deleteProductActivity, deleteProductView, apiService, appDatabase)
        }
    }

    @Binds
    abstract fun provideDeleteProductView(deleteProductActivity: DeleteProductActivity) : DeleteProductView


}