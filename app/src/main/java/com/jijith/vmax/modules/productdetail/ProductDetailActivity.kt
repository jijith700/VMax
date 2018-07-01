package com.jijith.vmax.modules.productdetail

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.jijith.vmax.R
import com.jijith.vmax.adapter.StockAdapter
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.utils.Constants
import com.jijith.vmax.utils.VerticalDividerItemDecoration
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import java.io.File

class ProductDetailActivity : BaseActivity(), ProductDetailView {

    lateinit var productWithStock: ProductWithStock

    override fun getLayout(): Int {
        return R.layout.activity_product_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    private fun initViews() {
        productWithStock = intent.getParcelableExtra(Constants.PRODUCT_WITH_STOCK)

        val ivProduct = findViewById<ImageView>(R.id.iv_product)
        val tvProductName = findViewById<TextView>(R.id.tv_product_name)
        val rvStockList = findViewById<RecyclerView>(R.id.rv_stock)

        Picasso.with(this)
                .load(File(productWithStock.product!!.imagePath))
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(ivProduct)

        tvProductName.text = productWithStock.product!!.productName

        val adapter = StockAdapter(this);
        adapter.updateAdapter(productWithStock.stock!!)

        val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(1, 1)
        rvStockList.layoutManager = layoutManager
        rvStockList.addItemDecoration(VerticalDividerItemDecoration(20, false))
        rvStockList.itemAnimator = DefaultItemAnimator()
        rvStockList.adapter = adapter
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
