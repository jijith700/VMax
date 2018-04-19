package com.jijith.vmax.modules.chekout

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.jijith.vmax.R
import com.jijith.vmax.adapter.CheckOutAdapter
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.dialog.DiscountDialogFragment
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.modules.home.all_products.AllProductFragment
import com.jijith.vmax.utils.*
import java.util.*
import javax.inject.Inject

class CheckOutActivity : BaseActivity(), CheckOutView, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {

        if (viewHolder is CheckOutAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            val productName = products[viewHolder.getAdapterPosition()].product!!.productName

            // backup of removed item for undo purpose
            val deletedItem = products[viewHolder.getAdapterPosition()]
            val deletedIndex = viewHolder.getAdapterPosition()

            adapter.removeItem(viewHolder.getAdapterPosition())

            val snackbar = Snackbar
                    .make(productList, productName + " removed from cart!", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO", View.OnClickListener() {
                // undo is selected, restore the deleted item
                adapter.restoreItem(deletedItem, deletedIndex)
            });
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        }
    }

    override fun onCompleteCheckOut() {
        AppLog.d(TAG!!, "Sales Completed...")
        Utils.Companion.savePreferences(this, Constants.CHECK_OUT, true)

    }

    companion object {
        val TAG = AllProductFragment::class.simpleName
    }

    @BindView(R.id.rv_products)
    lateinit var productList: RecyclerView
    @BindView(R.id.tv_total_price)
    lateinit var totalPrice: TextView

    @Inject
    lateinit var checkOutPresenter: CheckOutPresenter

    private lateinit var discountDialogFragment: DiscountDialogFragment
    private lateinit var adapter: CheckOutAdapter

    private var products: ArrayList<ProductWithStock> = ArrayList()

    private var name: String = ""
    private var phone: String = ""
    private var discount: Int = 0
    private var commission: Int = 0

    override fun getLayout(): Int {
        return R.layout.activity_check_out
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_check_out)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.checkout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_nominal -> {
                if (!discountDialogFragment.isAdded) {
                    discountDialogFragment.show(supportFragmentManager, getString(R.string.hint_discount))
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun setAdapter(adapter: CheckOutAdapter) {
        val mLayoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(1, 1)
        productList.layoutManager = mLayoutManager
        productList.addItemDecoration(VerticalDividerItemDecoration(20, false))
        productList.itemAnimator = DefaultItemAnimator()
        productList.adapter = adapter
        this.adapter = adapter

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(productList)

    }

    override fun onChangeTotalPrice(price: Int) {
        totalPrice.text = String.format(getString(R.string.txt_total_rs), price)
    }

    override fun onChangeCustomerDetails(name: String, phone: String, discount: Int, commission: Int) {
        this.name = name
        this.phone = phone
        this.discount = discount
        this.commission = commission
    }

    override fun onErrorName(msg: String) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        if (!discountDialogFragment.isAdded) {
            discountDialogFragment.show(supportFragmentManager, getString(R.string.hint_discount))
        }
    }

    private fun initView() {

        products = intent.getParcelableArrayListExtra(Constants.PRODUCT_LIST)
        discountDialogFragment = DiscountDialogFragment()
        discountDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        discountDialogFragment.setCheckOutView(this)

        checkOutPresenter.getId()
        checkOutPresenter.setAdapter(products)

    }

    @OnClick(R.id.btn_check_out)
    fun onClickCheckOut() {
        checkOutPresenter.checkOut(name, phone, discount, commission)

    }
}
