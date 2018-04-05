package com.jijith.vmax.modules.edit_product

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
 abstract class EditProductActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideEditProductPresenter(editProductActivity: EditProductActivity, editProductView: EditProductView,
                                        apiService: ApiService, appDatabase: AppDatabase): EditProductPresenter {
            return EditProductPresenterImpl(editProductActivity, editProductView, apiService, appDatabase)
        }
    }

    @Binds
    abstract fun provideEditProductView(editProductActivity: EditProductActivity) : EditProductView


}