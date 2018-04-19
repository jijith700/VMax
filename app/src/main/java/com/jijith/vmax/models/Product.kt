package com.jijith.vmax.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.R.attr.name



/**
 * Created by jijith on 1/23/18.
 */

@Entity(tableName = "productWithStock")
class Product() : Parcelable {

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0;
        var productName: String = "";
        var quantity: Int = 0;
        var imagePath: String = "";

        constructor(parcel: Parcel) : this() {
                id = parcel.readInt()
                productName = parcel.readString()
                quantity = parcel.readInt()
                imagePath = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(productName)
                parcel.writeInt(quantity)
                parcel.writeString(imagePath)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Product> {
                override fun createFromParcel(parcel: Parcel): Product {
                        return Product(parcel)
                }

                override fun newArray(size: Int): Array<Product?> {
                        return arrayOfNulls(size)
                }
        }

        override fun toString(): String {
                return productName
        }

}