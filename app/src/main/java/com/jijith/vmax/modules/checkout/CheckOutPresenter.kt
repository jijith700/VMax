package com.jijith.vmax.modules.checkout

import com.jijith.vmax.models.ProductWithStock

/**
 * Created by jijith on 12/25/17.
 */

interface CheckOutPresenter {

    fun getId()
    fun setAdapter(productWithStock: ArrayList<ProductWithStock>)
    fun setTotalPrice()
    fun checkOut(name: String, phone: String, discount: Int, commissionPaidTo: String,
                 commission: Int, purchaseMode: Int)
}