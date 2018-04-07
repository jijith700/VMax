package com.jijith.vmax.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Relation


class ProductWithStock {

    @Embedded
    var product: Product? = null

    @Relation(parentColumn = "id", entityColumn = "productId")
    var stock: List<Stock>? = null // or use simply 'List stock;'

    @Ignore
    var count: Int = 0
}