package com.jijith.vmax.modules.add_product

import com.jijith.vmax.models.Product
import com.jijith.vmax.models.Stock

/**
 * Created by jijith on 12/25/17.
 */

interface AddProductPresenter {

    fun getId()
    fun addStock(product : Product, stock: Stock)
}