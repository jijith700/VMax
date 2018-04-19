package com.jijith.vmax.modules.productdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jijith.vmax.R
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.utils.Constants
import dagger.android.AndroidInjection

class ProductDetailActivity : BaseActivity(), ProductDetailView {

    lateinit var productWithStock: ProductWithStock

    override fun getLayout(): Int {
        return R.layout.activity_product_detail;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        productWithStock = intent.getParcelableExtra(Constants.PRODUCT_WITH_STOCK)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
