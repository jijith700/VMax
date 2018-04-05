package com.jijith.vmax.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jijith on 1/23/18.
 */
@Entity (tableName = "stock")
open class Stock(

        @PrimaryKey(autoGenerate = true)
        open var id: Int = 0,

        open var productId: Int = 0,
        open var purchaseDate: String = "",
        open var quantity: Int = 0,
        open var stockPrice: Int = 0,
        open var salePrice: Int = 0,
        open var discount: Int = 0,
        open var balanceStock: Int = 0,
        open var isClosed: Boolean = false

)
