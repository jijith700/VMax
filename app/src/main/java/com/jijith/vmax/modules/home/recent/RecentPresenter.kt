package com.jijith.vmax.modules.home.recent

import android.content.Context
import com.jijith.vmax.adapter.RecentProductAdapter
import com.jijith.vmax.utils.AppLog
import javax.inject.Inject

/**
 * Created by jijith on 12/25/17.
 */

class RecentPresenter @Inject constructor(internal var recentView: RecentView) {

//    @Inject lateinit var recentProductAdapter : HomeFragmentProvider()

    lateinit var recentProductAdapter : RecentProductAdapter

    fun getRecentProducts(context : Context) {

        recentProductAdapter = RecentProductAdapter(context)

//        val products : List<Product> = realm.where(Product::class.java).findAll().toList()
//        val stocks : List<Stock> = realm.where(Stock::class.java).findAll().toList()

//        recentProductAdapter.updateAdapter(products, stocks)

        AppLog.e("Size", "" + recentProductAdapter.itemCount)

        recentView.setAdapter(recentProductAdapter)
    }

    fun updateRecentProduct(context : Context) {

//        val products : List<Product> = realm.where(Product::class.java).findAll().toList()
//        val stocks : List<Stock> = realm.where(Stock::class.java).findAll().toList()


//        recentProductAdapter.updateAdapter(products, stocks)

        AppLog.e("Size", "" + recentProductAdapter.itemCount)

        recentView.setAdapter(recentProductAdapter)
    }

}