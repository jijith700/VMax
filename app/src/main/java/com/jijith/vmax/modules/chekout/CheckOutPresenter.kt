package com.jijith.vmax.modules.chekout

import com.jijith.vmax.models.Product
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.models.Stock

/**
 * Created by jijith on 12/25/17.
 */

interface CheckOutPresenter {

    fun getId()
    fun setAdapter(productWithStock: ArrayList<ProductWithStock>)
    fun setTotalPrice()
    fun checkOut(name: String, phone: String, discount: Int, commission: Int)
}