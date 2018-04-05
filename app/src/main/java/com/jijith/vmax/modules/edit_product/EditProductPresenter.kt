package com.jijith.vmax.modules.edit_product

import android.content.Context
import com.jijith.vmax.models.Product

/**
 * Created by jijith on 12/25/17.
 */

interface EditProductPresenter {
    fun editProduct(product : Product, fileName: String)
    fun getProducts(context: Context)
}