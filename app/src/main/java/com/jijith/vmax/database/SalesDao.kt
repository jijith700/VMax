package com.jijith.vmax.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.models.Sales
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by jijith on 4/3/18.
 */
@Dao
public interface SalesDao {

    @Query("select max(id) from sales")
    fun getItemId(): Single<Int>

    @Query("select * from sales")
    fun getAllSalesItems(): Flowable<List<Sales>>

    @Query("select * from sales where id = :id")
    fun getItembyId(id: String): Sales

    @Insert(onConflict = REPLACE)
    fun addSales(sales: Sales)

    @Insert(onConflict = REPLACE)
    fun addSales(sales: List<Sales>)

    @Delete
    fun deleteSales(sales: Sales)

}