package com.jijith.vmax.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.jijith.vmax.models.Stock
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by jijith on 4/3/18.
 */
@Dao
public interface StockDao {

    @Query("select max(id) from stock")
    fun getItemId(): Single<Int>

    @Query("select * from stock")
    fun getAllStockItems(): Flowable<List<Stock>>

    @Query("select * from stock where id = :id")
    fun getItembyId(id: String): Stock

    @Query("update stock set balanceStock  = :balanceStock where id = :id")
    fun update(balanceStock: Int, id: Int)

    @Query("update stock set balanceStock  = :balanceStock, isClosed = :isClosed where id = :id")
    fun update(balanceStock: Int, isClosed: Boolean, id: Int)

    @Update
    fun update(stock: Stock)

    @Insert(onConflict = REPLACE)
    fun addStock(stock: Stock)

    @Delete
    fun deleteStock(stock: Stock)

}