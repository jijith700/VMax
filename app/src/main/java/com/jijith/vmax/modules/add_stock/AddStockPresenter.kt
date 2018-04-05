package com.jijith.vmax.modules.add_stock
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.Stock

/**
 * Created by jijith on 12/25/17.
 */

interface AddStockPresenter {

    fun getId()
    fun addStock(product : Product, stock: Stock)
}