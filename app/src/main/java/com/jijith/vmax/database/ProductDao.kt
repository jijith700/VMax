package com.jijith.vmax.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.ProductWithStock
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by jijith on 4/3/18.
 */
@Dao
interface ProductDao {

    @Query("SELECT max(id) FROM product")
    fun getItemId() : Single<Int>

    @Query("SELECT * FROM product")
    fun getAllProductItems(): Flowable<List<Product>>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getItembyId(id: String): Product

    @Transaction
    @Query("SELECT * FROM product")
    fun getProductWithStock(): Flowable<List<ProductWithStock>>

    @Insert(onConflict = REPLACE)
    fun addProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

}