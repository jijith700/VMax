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
interface StockDao {

    @Query("SELECT max(id) FROM stock")
    fun getItemId(): Single<Int>

    @Query("SELECT * FROM stock")
    fun getAllStockItems(): Flowable<List<Stock>>

    @Query("SELECT * FROM stock WHERE isClosed = :isClosed")
    fun getAllStockItems(isClosed: Boolean): Flowable<List<Stock>>

    @Query("SELECT * FROM stock WHERE id = :id")
    fun getItembyId(id: String): Stock

    @Query("UPDATE stock SET balanceStock  = :balanceStock WHERE id = :id")
    fun update(balanceStock: Int, id: Int)

    @Query("UPDATE stock SET balanceStock  = :balanceStock, isClosed = :isClosed WHERE id = :id")
    fun update(balanceStock: Int, isClosed: Boolean, id: Int)

    @Update
    fun update(stock: Stock)

    @Insert(onConflict = REPLACE)
    fun addStock(stock: Stock)

    @Delete
    fun deleteStock(stock: Stock)

}