package com.jijith.vmax.modules.home.all_products


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import butterknife.BindView
import com.jijith.vmax.R
import com.jijith.vmax.adapter.ProductListAdapter
import com.jijith.vmax.base.BaseFragment
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.utils.VerticalDividerItemDecoration
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class AllProductFragment : BaseFragment(), AllProductView {

    companion object {
        val TAG = AllProductFragment::class.simpleName
    }

    @BindView(R.id.rv_products)
    lateinit var productList: RecyclerView

    @Inject
    lateinit var allProductPresenter: AllProductPresenter

    override fun getLayoutId(): Int {
        return R.layout.fragment_all_product
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)

        allProductPresenter.getProducts()
    }

    override fun setAdapter(adapter: ProductListAdapter) {
        val mLayoutManager : RecyclerView.LayoutManager = StaggeredGridLayoutManager(1, 1)
        productList.layoutManager = mLayoutManager
        productList.addItemDecoration(VerticalDividerItemDecoration(20, false))
        productList.itemAnimator = DefaultItemAnimator()
        productList.adapter = adapter
    }

    fun searchProduct(keyword: String) {
        allProductPresenter.search(keyword)
    }

    fun resetAdapter() {
        allProductPresenter.resetAdapter()
    }
}
