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
public interface InvoiceDao {

    @Query("select max(id) from invoice")
    fun getItemId(): Single<Int>

    @Query("select * from invoice")
    fun getAllInvoiceItems(): Flowable<List<Invoice>>

    @Query("select * from invoice where id = :id")
    fun getItembyId(id: String): Invoice

    @Insert(onConflict = REPLACE)
    fun addInvoice(invoice: Invoice)

    @Delete
    fun deleteInvoice(invoice: Invoice)

}