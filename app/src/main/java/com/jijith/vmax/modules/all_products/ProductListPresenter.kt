package com.jijith.vmax.modules.all_products

import android.content.Context

/**
 * Created by jijith on 12/25/17.
 */

interface ProductListPresenter {
    fun getProducts(context: Context)
    fun updateAdapter(context: Context)
    fun searchProduct(keyword: String)
    fun resetAdapter()
}