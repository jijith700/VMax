package com.jijith.vmax.modules.all_products

import android.content.Context
import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.utils.AppLog
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import javax.inject.Inject


/**
 * Created by jijith on 12/25/17.
 */
class ProductListPresenterImpl @Inject constructor(private var context: ProductListActivity, private var productListView: ProductListView,
                                                   private var apiService: ApiService, private var appDatabase: AppDatabase) : ProductListPresenter {


    companion object {
        val TAG : String = ProductListPresenterImpl::class.java.simpleName;
    }

    lateinit var productListAdapter: ProductListAdapter
    private var products = ArrayList<ProductWithStock>();

    override fun getProducts(context: Context) {
        productListAdapter = ProductListAdapter(context)

        appDatabase.productModel().getProductWithStock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<List<ProductWithStock>> {
                    override fun accept(t: List<ProductWithStock>) {
                        productListAdapter.updateAdapter(t)
                        products = t as ArrayList<ProductWithStock>
                    }
                })


        AppLog.e("Size", "" + productListAdapter.itemCount)

        productListView.setAdapter(productListAdapter)
    }

    override fun updateAdapter(context: Context) {
//        productListAdapter.updateAdapter(realm.where(Product::class.java).findAll().toList())

        AppLog.e("Size", "" + productListAdapter.itemCount)
    }

    override fun resetAdapter() {
        productListAdapter.updateAdapter(products)
    }

    override fun searchProduct(keyword: String) {
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