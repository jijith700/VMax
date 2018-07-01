package com.jijith.vmax.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import com.jijith.vmax.utils.TimestampConverter
import java.util.*

/**
 * Created by jijith on 1/23/18.
 */
@Entity(tableName = "stock")
class Stock() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var productId: Int = 0
    var purchaseDate: Date? = null
    var quantity: Int = 0
    var stockPrice: Int = 0
    var salePrice: Int = 0
    var discount: Int = 0
    var balanceStock: Int = 0
    var isClosed: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        productId = parcel.readInt()
        purchaseDate = parcel.readDate()
        quantity = parcel.readInt()
        stockPrice = parcel.readInt()
        salePrice = parcel.readInt()
        discount = parcel.readInt()
        balanceStock = parcel.readInt()
        isClosed = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(productId)
        parcel.writeDate(purchaseDate)
        parcel.writeInt(quantity)
        parcel.writeInt(stockPrice)
        parcel.writeInt(salePrice)
        parcel.writeInt(discount)
        parcel.writeInt(balanceStock)
        parcel.writeByte(if (isClosed) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stock> {
        override fun createFromParcel(parcel: Parcel): Stock {
            return Stock(parcel)
        }

        override fun newArray(size: Int): Array<Stock?> {
            return arrayOfNulls(size)
        }
    }

    fun Parcel.writeDate(date: Date?) {
        writeLong(date?.time ?: -1)
    }

    fun Parcel.readDate(): Date? {
        val long = readLong()
        return if (long != -1L) Date(long) else null
    }
}
