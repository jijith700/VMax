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
public interface ProductDao {

    @Query("select max(id) from productWithStock")
    fun getItemId() : Single<Int>

    @Query("select * from productWithStock")
    fun getAllProductItems(): Flowable<List<Product>>

    @Query("select * from productWithStock where id = :id")
    fun getItembyId(id: String): Product

    @Transaction
    @Query("select * from productWithStock")
    fun getProductWithStock(): Flowable<List<ProductWithStock>>

    @Insert(onConflict = REPLACE)
    fun addProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

}