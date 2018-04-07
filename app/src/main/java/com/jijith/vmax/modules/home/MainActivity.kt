package com.jijith.vmax.modules.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.jijith.vmax.R
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.modules.add_product.AddProductActivity
import com.jijith.vmax.modules.add_stock.AddStockActivity
import com.jijith.vmax.modules.all_products.ProductListActivity
import com.jijith.vmax.modules.delete_product.DeleteProductActivity
import com.jijith.vmax.modules.home.all_products.AllProductFragment
import com.jijith.vmax.modules.home.recent.RecentFragment
import com.jijith.vmax.utils.AppLog
import com.jijith.vmax.utils.BadgeDrawable
import com.jijith.vmax.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    @BindView(R.id.vp_product)
    lateinit var mViewPager: ViewPager
    @BindView(R.id.tab_layout)
    lateinit var mTabLayout: TabLayout

    @Inject lateinit var mainPresenter: MainPresenter

    private var allProductFragment: AllProductFragment = AllProductFragment()
    private var recentFragment: RecentFragment = RecentFragment()
//    private lateinit var cartNotification : TextView
    private lateinit var cartNotification : LayerDrawable

    private var products: List<ProductWithStock> = ArrayList()

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        setViewPager(mViewPager)
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"))
        mViewPager.offscreenPageLimit = 4


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        val item = menu.findItem(R.id.action_cart)
//        item.setActionView(R.layout.layout_actionbar_badge)
//        val badgeLayout = item.actionView as RelativeLayout
//        cartNotification = badgeLayout.findViewById(R.id.cart_notification) as TextView

        cartNotification = item.getIcon() as LayerDrawable

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_all_products -> {
                val intentAllProduct = Intent(this, ProductListActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentAllProduct)
            }
            R.id.nav_add_product -> {
                val intentStock = Intent(this, AddProductActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentStock)
            }
//            R.id.nav_edit_product -> {
//                val intentDelete = Intent(this, EditProductActivity::class.java)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intentDelete)
//            }
            R.id.nav_delete_product -> {
                val intentDelete = Intent(this, DeleteProductActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentDelete)
            }
            R.id.nav_add_stock -> {
                val intentStock = Intent(this, AddStockActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentStock)
            }
            R.id.nav_edit_stock -> {

            }
            R.id.nav_delete_stock -> {

            }

            R.id.nav_logout -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onAddedToCart(productWithStock: ProductWithStock) {

        var count = 0
        var isFound = false

        if (products.isNotEmpty()) {
            for (product in products) {
                if (product.product!!.id == productWithStock.product!!.id) {
                    product.count = productWithStock.count
                    isFound = true
                }
                count += product.count
            }
        }

        if (!isFound) {
            products += productWithStock
            count += productWithStock.count
        }

//        if (cartNotification.visibility == View.GONE)
//            cartNotification.visibility = View.VISIBLE
//
//        cartNotification.text = count.toString()

        Utils.setBadgeCount(this, cartNotification, count.toString())


        AppLog.d(TAG!!, count.toString())

    }

    private fun setViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(allProductFragment, getString(R.string.all_product))
        adapter.addFragment(recentFragment, getString(R.string.recent_products))
        viewPager.adapter = adapter
    }

    private inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}
