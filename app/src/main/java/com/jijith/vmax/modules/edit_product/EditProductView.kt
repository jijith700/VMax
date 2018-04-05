package com.jijith.vmax.modules.edit_product

/**
 * Created by jijith on 12/25/17.
 */
interface EditProductView {
    fun onProductUpdated(msg : String)
    fun onError(msg: String)

    fun onErrorProductImage(msg : String)
    fun onErrorStockName(msg : String)
    fun onErrorPurchaseDate(msg : String)
    fun onErrorQuantity(msg : String)
    fun onErrorStockPrice(msg : String)
    fun onErrorSalePrice(msg : String)

}