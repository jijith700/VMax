package com.jijith.vmax.modules.delete_product

import com.jijith.vmax.adapter.DeleteProductAdapter

/**
 * Created by jijith on 12/25/17.
 */
interface DeleteProductView {
    fun onProductDeleted(msg : String)
    fun onError(msg: String)

    fun setAdapter(adapter: DeleteProductAdapter)
}