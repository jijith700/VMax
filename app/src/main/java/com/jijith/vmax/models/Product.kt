package com.jijith.vmax.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jijith on 1/23/18.
 */

@Entity (tableName = "product")
open class Product(

        @PrimaryKey(autoGenerate = true)
        open var id: Int = 0,
        open var productName: String = "",
        open var quantity: Int = 0,
        open var imagePath: String = ""

)