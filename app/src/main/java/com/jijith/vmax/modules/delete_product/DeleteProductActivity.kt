package com.jijith.vmax.modules.delete_product

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import com.jijith.vmax.R
import com.jijith.vmax.adapter.DeleteProductAdapter
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.models.Product
import com.jijith.vmax.utils.AppLog
import com.jijith.vmax.utils.Constants
import com.jijith.vmax.utils.Utils
import dagger.android.AndroidInjection
import javax.inject.Inject

class DeleteProductActivity : BaseActivity(), DeleteProductView {

    companion object {
        val TAG = DeleteProductActivity::class.java.simpleName
    }

    @BindView(R.id.rv_products)
    lateinit var productList: RecyclerView

    @Inject
    lateinit var deleteProductPresenter: DeleteProductPresenter

    override fun getLayout(): Int {
        return R.layout.activity_delete_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_delete_product)

        AndroidInjection.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        deleteProductPresenter.getProducts(this)


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

    override fun setAdapter(adapter: DeleteProductAdapter) {
//        val mLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity, VERTICAL, false)
        val mLayoutManager : RecyclerView.LayoutManager = StaggeredGridLayoutManager(1, 1)
        productList.layoutManager = mLayoutManager
        productList.itemAnimator = DefaultItemAnimator()
        productList.adapter = adapter
    }

    override fun onProductDeleted(msg: String) {
        Utils.showSnackBar(productList, msg)
        deleteProductPresenter.updateAdapter(this)

        Utils.SavePreferences(this, Constants.PRODUCT_CHANGED, true)
    }

    override fun onError(msg: String) {
        AppLog.e(TAG, msg)
    }

    fun deleteProduct(product: Product) {
        deleteProductPresenter.deleteProduct(product)
    }

}
