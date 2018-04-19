package com.jijith.vmax.modules.home.all_products

import android.content.Context
import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.modules.home.MainView
import com.jijith.vmax.utils.AppLog
import io.reactivex.android.schedulers.AndroidSchedulers
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
    private var products = ArrayList<ProductWithStock>();

    fun getProducts() {
        productListAdapter = ProductListAdapter(context, mainView)

        appDatabase.productModel().getProductWithStock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t ->
                    productListAdapter.updateAdapter(t)
                    products = t as ArrayList<ProductWithStock>
                }


        AppLog.e("Size", "" + productListAdapter.itemCount)

        allProductView.setAdapter(productListAdapter)
    }

    fun resetAdapter() {
        productListAdapter.updateAdapter(products)
    }

    fun search(keyword: String) {
        productListAdapter.updateAdapter(filter(keyword))
    }

    private fun filter(keyword: String): ArrayList<ProductWithStock> {
        val tempProduct = ArrayList<ProductWithStock>()
        if (productListAdapter != null) {
            if (productListAdapter.itemCount > 0) {
                for (product in products) {
                    if (product.product!!.productName.toUpperCase().contains(keyword)) {
                        tempProduct.add(product)
                    }
                }
            }
        }

//        if (tempCourse.isEmpty())
//            mError.setVisibility(View.VISIBLE)
//        else
//            mError.setVisibility(View.GONE)

        return tempProduct
    }
}