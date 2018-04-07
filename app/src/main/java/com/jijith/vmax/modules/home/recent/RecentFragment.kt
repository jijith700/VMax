package com.jijith.vmax.modules.home.recent


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import butterknife.BindView
import com.jijith.vmax.R
import com.jijith.vmax.adapter.RecentProductAdapter
import com.jijith.vmax.base.BaseFragment
import com.jijith.vmax.utils.Constants
import com.jijith.vmax.utils.Utils
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class RecentFragment : BaseFragment() , RecentView {

    companion object {
        var TAG : String = RecentFragment::class.java.simpleName
    }

    @BindView(R.id.rv_products)
    lateinit var productList: RecyclerView

    @Inject lateinit var recentPresenter: RecentPresenter

    override
    fun getLayoutId(): Int {
        return R.layout.fragment_recent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AndroidSupportInjection.inject(this)

        recentPresenter.getRecentProducts(activity!!.applicationContext)

    }

    override fun setAdapter(adapter: RecentProductAdapter) {
//        val mLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity, VERTICAL, false)
        val mLayoutManager : RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, 1)
        productList.layoutManager = mLayoutManager
        productList.itemAnimator = DefaultItemAnimator()
        productList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        if (Utils.loadPreferencesBoolean(activity!!.applicationContext, Constants.PRODUCT_CHANGED)) {
            recentPresenter.updateRecentProduct(activity!!.applicationContext)
            Utils.savePreferences(activity!!.applicationContext, Constants.PRODUCT_CHANGED, false)
        }
    }
}