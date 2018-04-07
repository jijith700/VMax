package com.jijith.vmax.modules.home.all_products

import android.content.Context
import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.modules.home.MainView
import com.jijith.vmax.utils.AppLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jijith on 12/25/17.
 */

class AllProductPresenter @Inject constructor(internal var allProductView: AllProductView,
                                              internal var appDatabase: AppDatabase,
                                              internal var context : Context,
                                              internal var mainView: MainView) {

    lateinit var productListAdapter: ProductListAdapter

    fun getProducts() {
        productListAdapter = ProductListAdapter(context, mainView)

        appDatabase.productModel().getProductWithStock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t -> productListAdapter.updateAdapter(t) }


        AppLog.e("Size", "" + productListAdapter.itemCount)

        allProductView.setAdapter(productListAdapter)
    }
}