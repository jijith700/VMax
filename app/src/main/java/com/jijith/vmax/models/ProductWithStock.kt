package com.jijith.vmax.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Relation
import android.os.Parcel
import android.os.Parcelable


class ProductWithStock() : Parcelable {

    @Embedded
    var product: Product? = null

    @Relation(parentColumn = "id", entityColumn = "productId")
    var stock: List<Stock>? = null // or use simply 'List stock;'

    @Ignore
    var count: Int = 0

    @Ignore
    var totalPrice: Int = 0

    constructor(parcel: Parcel) : this() {
        product = parcel.readParcelable(Product::class.java.classLoader)
        stock = parcel.createTypedArrayList(Stock)
        count = parcel.readInt()
        totalPrice = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(product, flags)
        parcel.writeTypedList(stock)
        parcel.writeInt(count)
        parcel.writeInt(totalPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductWithStock> {
        override fun createFromParcel(parcel: Parcel): ProductWithStock {
            return ProductWithStock(parcel)
        }

        override fun newArray(size: Int): Array<ProductWithStock?> {
            return arrayOfNulls(size)
        }
    }
}