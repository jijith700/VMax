package com.jijith.vmax.modules.productdetail

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import javax.inject.Inject


/**
 * Created by jijith on 12/25/17.
 */
class ProductDetailPresenterImpl @Inject constructor(private var context: ProductDetailActivity, private var productDetailView: ProductDetailView,
                                                     private var apiService: ApiService, private var appDatabase: AppDatabase) : ProductDetailPresenter {


    companion object {
        val TAG: String = ProductDetailPresenterImpl::class.java.simpleName;
    }
}