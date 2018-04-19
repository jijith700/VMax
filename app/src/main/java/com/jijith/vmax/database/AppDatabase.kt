package com.jijith.vmax.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jijith.vmax.R
import com.jijith.vmax.models.Invoice
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.Sales
import com.jijith.vmax.models.Stock

/**
 * Created by jijith on 4/3/18.
 */
@Database(entities = arrayOf(Product::class, Stock::class, Invoice::class, Sales::class), version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, context.getString(R.string.app_name))
                        .build()
            }

            return appDatabase as AppDatabase
        }

        fun destroyInstance() {
            appDatabase = null
        }

    }

    abstract fun productModel(): ProductDao
    abstract fun stockModel(): StockDao
    abstract fun invoiceModel(): InvoiceDao
    abstract fun salesModel(): SalesDao

}