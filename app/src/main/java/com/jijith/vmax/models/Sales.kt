package com.jijith.vmax.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by jijith on 2/1/18.
 */
@Entity
open class Sales(

        @PrimaryKey(autoGenerate = true)
        open var id: Int = 0,

        open var productId: Int = 0,
        open var productName: String = "",
        open var quantity: Int = 0,
        open var salePrice: Int = 0,
        open var commission: Int = 0,
        open var discount: Int = 0

)