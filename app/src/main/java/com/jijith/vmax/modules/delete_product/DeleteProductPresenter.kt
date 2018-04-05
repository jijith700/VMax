package com.jijith.vmax.modules.delete_product

import android.content.Context
import com.jijith.vmax.models.Product

/**
 * Created by jijith on 12/25/17.
 */

interface DeleteProductPresenter {
    fun deleteProduct(product : Product)
    fun getProducts(context: Context)
    fun updateAdapter(context: Context)
}