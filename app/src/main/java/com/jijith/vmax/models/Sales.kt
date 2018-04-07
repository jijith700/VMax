package com.jijith.vmax.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable


/**
 * Created by jijith on 2/1/18.
 */
@Entity
class Sales() : Parcelable {

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
        var productId: Int = 0
        var productName: String = ""
        var saleDate : String = ""
        var quantity: Int = 0
        var salePrice: Int = 0
        var commission: Int = 0
        var discount: Int = 0

        constructor(parcel: Parcel) : this() {
                id = parcel.readInt()
                productId = parcel.readInt()
                productName = parcel.readString()
                saleDate = parcel.readString()
                quantity = parcel.readInt()
                salePrice = parcel.readInt()
                commission = parcel.readInt()
                discount = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeInt(productId)
                parcel.writeString(productName)
                parcel.writeString(saleDate)
                parcel.writeInt(quantity)
                parcel.writeInt(salePrice)
                parcel.writeInt(commission)
                parcel.writeInt(discount)
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