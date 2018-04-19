package com.jijith.vmax.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable


/**
 * Created by jijith on 2/1/18.
 */
@Entity(tableName = "sales")
class Sales() : Parcelable {

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
        var productId: Int = 0
    var stockId: Int = 0
        var productName: String = ""
        var saleDate : String = ""
        var quantity: Int = 0
    var unitPrice: Int = 0
        var salePrice: Int = 0

        constructor(parcel: Parcel) : this() {
                id = parcel.readInt()
                productId = parcel.readInt()
            stockId = parcel.readInt()
                productName = parcel.readString()
                saleDate = parcel.readString()
                quantity = parcel.readInt()
            unitPrice = parcel.readInt()
                salePrice = parcel.readInt()
        }

    @Ignore
    constructor(id: Int, productId: Int, stockId: Int, productName: String, saleDate: String,
                quantity: Int, unitPrice: Int, salePrice: Int) : this() {
        this.id = id
        this.productId = productId
        this.stockId = stockId
        this.productName = productName
        this.saleDate = saleDate
        this.quantity = quantity
        this.unitPrice = unitPrice
        this.salePrice = salePrice
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeInt(productId)
            parcel.writeInt(stockId)
                parcel.writeString(productName)
                parcel.writeString(saleDate)
                parcel.writeInt(quantity)
            parcel.writeInt(unitPrice)
                parcel.writeInt(salePrice)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Sales> {
                override fun createFromParcel(parcel: Parcel): Sales {
                        return Sales(parcel)
                }

                override fun newArray(size: Int): Array<Sales?> {
                        return arrayOfNulls(size)
                }
        }

}