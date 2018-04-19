package com.jijith.vmax.base

import com.jijith.vmax.modules.add_product.AddProductActivity
import com.jijith.vmax.modules.add_product.AddProductActivityModule
import com.jijith.vmax.modules.add_stock.AddStockActivity
import com.jijith.vmax.modules.add_stock.AddStockActivityModule
import com.jijith.vmax.modules.all_products.ProductListActivity
import com.jijith.vmax.modules.all_products.ProductListActivityModule
import com.jijith.vmax.modules.chekout.CheckOutActivity
import com.jijith.vmax.modules.chekout.CheckOutActivityModule
import com.jijith.vmax.modules.delete_product.DeleteProductActivity
import com.jijith.vmax.modules.delete_product.DeleteProductActivityModule
import com.jijith.vmax.modules.edit_product.EditProductActivity
import com.jijith.vmax.modules.edit_product.EditProductActivityModule
import com.jijith.vmax.modules.home.HomeFragmentProvider
import com.jijith.vmax.modules.home.MainActivity
import com.jijith.vmax.modules.home.MainActivityModule
import com.jijith.vmax.modules.login.LoginActivity
import com.jijith.vmax.modules.login.LoginActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jijith on 12/26/17.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class,
            HomeFragmentProvider::class))
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = arrayOf(AddProductActivityModule::class))
    abstract fun bindAddProductActivity(): AddProductActivity

    @ContributesAndroidInjector(modules = arrayOf(AddStockActivityModule::class))
    abstract fun bindAddStockActivity(): AddStockActivity


    @ContributesAndroidInjector(modules = arrayOf(DeleteProductActivityModule::class))
    abstract fun bindDeleteProductActivity(): DeleteProductActivity

    @ContributesAndroidInjector(modules = arrayOf(ProductListActivityModule::class))
    abstract fun bindProductListActivity(): ProductListActivity

    @ContributesAndroidInjector(modules = arrayOf(EditProductActivityModule::class))
    abstract fun bindEditProductActivity(): EditProductActivity

    @ContributesAndroidInjector(modules = arrayOf(CheckOutActivityModule::class))
    abstract fun bindCheckOutActivity(): CheckOutActivity

    /*
    * Fragments binding
    * */
//    @ContributesAndroidInjector(modules = arrayOf(AllProductModule::class))
//    abstract fun bindAllProductFragment(): AllProductFragment
//
//    @ContributesAndroidInjector(modules = arrayOf(RecentModule::class))
//    abstract fun bindRecentFragment(): RecentFragment

}