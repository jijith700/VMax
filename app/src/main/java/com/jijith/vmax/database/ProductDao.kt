package com.jijith.vmax.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.jijith.vmax.models.Product
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by jijith on 4/3/18.
 */
@Dao
public interface ProductDao {

    @Query("select max(id) from product")
    fun getItemId() : Single<Int>

    @Query("select * from product")
    fun getAllProductItems(): Maybe<List<Product>>

    @Query("select * from product where id = :id")
    fun getItembyId(id: String): Product

    @Insert(onConflict = REPLACE)
    fun addProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

}