package com.jijith.vmax.modules.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.jijith.vmax.R
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.modules.add_product.AddProductActivity
import com.jijith.vmax.modules.add_stock.AddStockActivity
import com.jijith.vmax.modules.all_products.ProductListActivity
import com.jijith.vmax.modules.delete_product.DeleteProductActivity
import com.jijith.vmax.modules.edit_product.EditProductActivity
import com.jijith.vmax.modules.home.recent.RecentFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    @Inject lateinit var mainPresenter: MainPresenter


    override fun getLayout(): Int {
        return R.layout.activity_main;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
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

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.layout_fragment, RecentFragment())
                .commit()
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
}
