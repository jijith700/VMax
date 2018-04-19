package com.jijith.vmax.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "invoice")
class Invoice() : Parcelable {

    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var phone: String = ""
    var grossAmount: Int = 0
    var netAmount: Int = 0
    var commission: Int = 0
    var discount: Int = 0

    @Ignore
    constructor(id: Int, name: String, phone: String, grossAmount: Int,
                netAmount: Int, commission: Int, discount: Int = 0) : this() {
        this.id = id
        this.name = name
        this.phone = phone
        this.grossAmount = grossAmount
        this.netAmount = netAmount
        this.commission = commission
        this.discount = discount
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        phone = parcel.readString()
        grossAmount = parcel.readInt()
        netAmount = parcel.readInt()
        commission = parcel.readInt()
        discount = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeInt(grossAmount)
        parcel.writeInt(netAmount)
        parcel.writeInt(commission)
        parcel.writeInt(discount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Invoice> {
        override fun createFromParcel(parcel: Parcel): Invoice {
            return Invoice(parcel)
        }

        override fun newArray(size: Int): Array<Invoice?> {
            return arrayOfNulls(size)
        }
    }


}