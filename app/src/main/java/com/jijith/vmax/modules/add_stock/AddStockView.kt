package com.jijith.vmax.modules.add_stock

import com.jijith.vmax.models.Product

/**
 * Created by jijith on 12/25/17.
 */
interface AddStockView {

    fun onGetProducts(products : List<Product>)
    fun onStockAdded(msg : String)
    fun onError(msg: String)

    fun onErrorProductImage(msg : String)
    fun onErrorStockName(msg : String)
    fun onErrorPurchaseDate(msg : String)
    fun onErrorQuantity(msg : String)
    fun onErrorStockPrice(msg : String)
    fun onErrorSalePrice(msg : String)
}