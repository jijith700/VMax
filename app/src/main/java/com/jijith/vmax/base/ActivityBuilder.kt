package com.jijith.vmax.base

import com.jijith.vmax.modules.add_product.AddProductActivity
import com.jijith.vmax.modules.add_product.AddProductActivityModule
import com.jijith.vmax.modules.add_stock.AddStockActivity
import com.jijith.vmax.modules.add_stock.AddStockActivityModule
import com.jijith.vmax.modules.all_products.ProductListActivity
import com.jijith.vmax.modules.all_products.ProductListActivityModule
import com.jijith.vmax.modules.checkout.CheckOutActivity
import com.jijith.vmax.modules.checkout.CheckOutActivityModule
import com.jijith.vmax.modules.delete_product.DeleteProductActivity
import com.jijith.vmax.modules.delete_product.DeleteProductActivityModule
import com.jijith.vmax.modules.edit_product.EditProductActivity
import com.jijith.vmax.modules.edit_product.EditProductActivityModule
import com.jijith.vmax.modules.home.HomeFragmentProvider
import com.jijith.vmax.modules.home.MainActivity
import com.jijith.vmax.modules.home.MainActivityModule
import com.jijith.vmax.modules.login.LoginActivity
import com.jijith.vmax.modules.login.LoginActivityModule
import com.jijith.vmax.modules.productdetail.ProductDetailActivity
import com.jijith.vmax.modules.productdetail.ProductDetailActivityModule
import com.jijith.vmax.modules.salesreport.SalesReportActivity
import com.jijith.vmax.modules.salesreport.SalesReportActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jijith on 12/26/17.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class,
        HomeFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [AddProductActivityModule::class])
    abstract fun bindAddProductActivity(): AddProductActivity

    @ContributesAndroidInjector(modules = [AddStockActivityModule::class])
    abstract fun bindAddStockActivity(): AddStockActivity


    @ContributesAndroidInjector(modules = [DeleteProductActivityModule::class])
    abstract fun bindDeleteProductActivity(): DeleteProductActivity

    @ContributesAndroidInjector(modules = [ProductListActivityModule::class])
    abstract fun bindProductListActivity(): ProductListActivity

    @ContributesAndroidInjector(modules = [EditProductActivityModule::class])
    abstract fun bindEditProductActivity(): EditProductActivity

    @ContributesAndroidInjector(modules = [CheckOutActivityModule::class])
    abstract fun bindCheckOutActivity(): CheckOutActivity

    @ContributesAndroidInjector(modules = [ProductDetailActivityModule::class])
    abstract fun bindProductDetailActivity(): ProductDetailActivity

    @ContributesAndroidInjector(modules = [SalesReportActivityModule::class])
    abstract fun bindSalesReportActivity(): SalesReportActivity

    /*
    * Fragments binding
    * */
//    @ContributesAndroidInjector(modules = arrayOf(AllProductModule::class))
//    abstract fun bindAllProductFragment(): AllProductFragment
//
//    @ContributesAndroidInjector(modules = arrayOf(RecentModule::class))
//    abstract fun bindRecentFragment(): RecentFragment

}