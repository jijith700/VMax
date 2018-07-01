package com.jijith.vmax.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.jijith.vmax.models.Invoice
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.models.Sales
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by jijith on 4/3/18.
 */
@Dao
interface InvoiceDao {

    @Query("SELECT max(id) FROM invoice")
    fun getItemId(): Single<Int>

    @Query("SELECT * FROM invoice")
    fun getAllInvoiceItems(): Flowable<List<Invoice>>

    @Query("SELECT * FROM invoice WHERE id = :id")
    fun getItembyId(id: String): Invoice

    @Insert(onConflict = REPLACE)
    fun addInvoice(invoice: Invoice)

    @Delete
    fun deleteInvoice(invoice: Invoice)

}