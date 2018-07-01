package com.jijith.vmax.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.jijith.vmax.models.Sales
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

/**
 * Created by jijith on 4/3/18.
 */
@Dao
interface SalesDao {

    @Query("SELECT max(id) FROM sales")
    fun getItemId(): Single<Int>

    @Query("SELECT * FROM sales")
    fun getAllSalesItems(): Flowable<List<Sales>>

    @Query("SELECT * FROM sales WHERE id = :id")
    fun getItembyId(id: String): Sales

    @Query("SELECT * FROM sales WHERE saleDate BETWEEN :fromDate AND :toDate")
    fun getSalesByDate(fromDate: Date, toDate: Date): Flowable<List<Sales>>

    @Insert(onConflict = REPLACE)
    fun addSales(sales: Sales)

    @Insert(onConflict = REPLACE)
    fun addSales(sales: List<Sales>)

    @Delete
    fun deleteSales(sales: Sales)

}