package com.jijith.vmax.modules.all_products

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import com.jijith.vmax.R
import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.base.BaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class ProductListActivity : BaseActivity(), ProductListView {

    companion object {
        val TAG = ProductListActivity::class.java.simpleName
    }

    @BindView(R.id.rv_products)
    lateinit var productList: RecyclerView

    @Inject
    lateinit var productListPresenter: ProductListPresenter

    override fun getLayout(): Int {
        return R.layout.activity_product_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_product_list)

        AndroidInjection.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        productListPresenter.getProducts(this)
    }


    override fun setAdapter(adapter: ProductListAdapter) {
//        val mLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity, VERTICAL, false)
        val mLayoutManager : RecyclerView.LayoutManager = StaggeredGridLayoutManager(1, 1)
        productList.layoutManager = mLayoutManager
        productList.itemAnimator = DefaultItemAnimator()
        productList.adapter = adapter
    }

    override fun onPostResume() {
        super.onPostResume()
//        if (Utils.LoadPreferencesBoolean(this, Constants.PRODUCT_CHANGED)) {
//            productListPresenter.updateAdapter(this)
////            Utils.SavePreferences(this, Constants.PRODUCT_CHANGED, false)
//        }
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
