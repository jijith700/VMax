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
    var commissionPaidTo: String = ""
    var commission: Int = 0
    var discount: Int = 0
    var purchaseMode: Int = 0

    @Ignore
    constructor(id: Int, name: String, phone: String, grossAmount: Int,
                netAmount: Int, commissionPaidTo: String, commission: Int, discount: Int = 0, purchaseMode: Int = 0) : this() {
        this.id = id
        this.name = name
        this.phone = phone
        this.grossAmount = grossAmount
        this.netAmount = netAmount
        this.commissionPaidTo = commissionPaidTo
        this.commission = commission
        this.discount = discount
        this.purchaseMode = purchaseMode
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        phone = parcel.readString()
        grossAmount = parcel.readInt()
        netAmount = parcel.readInt()
        commissionPaidTo = parcel.readString()
        commission = parcel.readInt()
        discount = parcel.readInt()
        purchaseMode = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeInt(grossAmount)
        parcel.writeInt(netAmount)
        parcel.writeString(commissionPaidTo)
        parcel.writeInt(commission)
        parcel.writeInt(discount)
        parcel.writeInt(purchaseMode)
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