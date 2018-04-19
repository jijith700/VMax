package com.jijith.vmax.modules.all_products

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import com.jijith.vmax.R
import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.utils.AppLog
import com.jijith.vmax.utils.Utils
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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.all_product_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.setIconified(false);

        val textChangeListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                AppLog.d(javaClass.simpleName, "on text change text: $newText")

                if (newText.isNotEmpty())
                    productListPresenter.searchProduct(newText.toUpperCase())
                else
                    productListPresenter.resetAdapter()

                return true
            }

            //
            override fun onQueryTextSubmit(query: String): Boolean {
                AppLog.d(javaClass.simpleName, "on query submit: $query")
                Utils.hideKeyboard(productList.context, productList)
                return true
            }
        }

        searchView.setOnQueryTextListener(textChangeListener)

        return true
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
