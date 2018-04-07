package com.jijith.vmax.modules.home

import com.jijith.vmax.models.ProductWithStock

/**
 * Created by jijith on 12/25/17.
 */
interface MainView {
//    fun onLoginSuccess(msg : String)
//    fun onLoginFailed(msg : String)
//    fun onErrorUserName(msg: String)
//    fun onErrorPassword(msg: String)

    fun onAddedToCart(productWithStock: ProductWithStock)
}