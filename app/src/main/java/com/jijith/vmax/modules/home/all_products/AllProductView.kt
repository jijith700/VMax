package com.jijith.vmax.modules.home.all_products

import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.adapter.RecentProductAdapter

/**
 * Created by jijith on 12/25/17.
 */
interface AllProductView {
//    fun onLoginSuccess(msg : String)
//    fun onLoginFailed(msg : String)
//    fun onErrorUserName(msg: String)
//    fun onErrorPassword(msg: String)
    fun setAdapter(adapter: ProductListAdapter)

}