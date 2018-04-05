package com.jijith.vmax.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.jijith.vmax.models.Stock
import io.reactivex.Single

/**
 * Created by jijith on 4/3/18.
 */
@Dao
public interface StockDao {

    @Query("select max(id) from stock")
    fun getItemId(): Single<Int>

    @Query("select * from stock")
    fun getAllStockItems(): LiveData<List<Stock>>

    @Query("select * from stock where id = :id")
    fun getItembyId(id: String): Stock

    @Insert(onConflict = REPLACE)
    fun addStock(stock: Stock)

    @Delete
    fun deleteStock(stock: Stock)

}