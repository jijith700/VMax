package com.jijith.vmax.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.jijith.vmax.models.Customer
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by jijith on 4/3/18.
 */
@Dao
interface CustomerDao {

    @Query("SELECT max(id) FROM customer")
    fun getItemId(): Single<Int>

    @Query("SELECT * FROM customer")
    fun getAllCustomerItems(): Flowable<List<Customer>>

    @Query("SELECT * FROM customer WHERE id = :id")
    fun getItembyId(id: String): Customer

    @Insert(onConflict = REPLACE)
    fun addCustomer(customer: Customer)

    @Delete
    fun deleteCustomer(customer: Customer)

}