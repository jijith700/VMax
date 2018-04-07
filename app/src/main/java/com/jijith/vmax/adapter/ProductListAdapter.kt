package com.jijith.vmax.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.numberbutton.NumberButton
import com.jijith.vmax.R
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.modules.edit_product.EditProductActivity
import com.jijith.vmax.modules.home.MainView
import com.jijith.vmax.utils.AppLog
import com.jijith.vmax.utils.Constants
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.io.File

/**
 * Created by jijith on 1/26/18.
 */
class ProductListAdapter(var context: Context?) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var products: List<ProductWithStock>
    private var mainView : MainView? = null

    constructor(context: Context?, mainView: MainView) : this(context) {
        this.mainView = mainView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(context, R.layout.layout_product_list_adapter, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val uri = Uri.fromFile(File(products[position].product?.imagePath))
        Picasso.with(context).invalidate(uri)
        Picasso.with(context)
                .load(uri)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.ivProduct)

        holder.tvProductName.text = products[position].product?.productName

        holder.tvStock.text = String.format(context!!.getString(R.string.txt_stock),
                products[position].stock!![0].balanceStock)

        holder.tvPrice.text = String.format(context!!.getString(R.string.txt_rs),
                products[position].stock!![0].salePrice)

        if (products[position].stock!!.size > 1) {

            var stock = 0
            for (i in 0 until products[position].stock!!.size - 1){
                stock += products[position].stock!![i].balanceStock
            }

            if (stock > 100) holder.nbQantity.setRange(1, 100)
            else holder.nbQantity.setRange(1, stock)

        } else {
            if (products[position].stock!![0].balanceStock == 0)
                holder.nbQantity.setRange(0, 0)
            else holder.nbQantity.setRange(1, products[position].stock!![0].balanceStock)
        }


        holder.btnAddToCart.setOnClickListener({

            if (holder.nbQantity.getIntNumber() == 0) {
                Toast.makeText(context, context!!.getString(R.string.error_item), Toast.LENGTH_SHORT).show()
            } else if (products[position].stock!!.size > 1) {

                var stock = 0
                for (i in 0 until products[position].stock!!.size - 1){
                    stock += products[position].stock!![i].balanceStock
                }

                if (products[position].count + holder.nbQantity.getIntNumber() > stock)
                    Toast.makeText(context, context!!.getString(R.string.error_stock), Toast.LENGTH_SHORT).show()
                else {
                    AppLog.d("NB", holder.nbQantity.getStringNumber())
                    products[position].count += holder.nbQantity.getIntNumber()

                    mainView!!.onAddedToCart(products[position])
                }

            } else {
                val stock = products[position].stock!![0].balanceStock

                if (stock == 0)
                    Toast.makeText(context, context!!.getString(R.string.error_item), Toast.LENGTH_SHORT).show()
                else {

                    if (products[position].count + holder.nbQantity.getIntNumber() > stock)
                        Toast.makeText(context, context!!.getString(R.string.error_stock), Toast.LENGTH_SHORT).show()
                    else {
                        AppLog.d("NB", holder.nbQantity.getStringNumber())
                        products[position].count += holder.nbQantity.getIntNumber()

                        mainView!!.onAddedToCart(products[position])
                    }
                }
            }



        })

        holder.btnMore.setOnClickListener({
            val popup = PopupMenu(context, holder.btnMore)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.product_popup_menu, popup.menu)

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener { item ->
                //                        toast.makeText(context,"You Clicked : " + item.getTitle(),toast.LENGTH_SHORT).show();
//                if (item.itemId == R.id.menu_cart) {
//                    (context as ProductListActivity).
//                } else
                    if (item.itemId == R.id.menu_edit) {
                    val intentEdit = Intent(context, EditProductActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(Constants.PRODUCT, products[position].product)
                    context?.startActivity(intentEdit)
                }
                true
            }

            popup.show()//showing popup menu
        })
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_product)
        lateinit var ivProduct: ImageView
        @BindView(R.id.tv_product_name)
        lateinit var tvProductName: TextView
        @BindView(R.id.tv_price)
        lateinit var tvPrice: TextView
        @BindView(R.id.tv_stock)
        lateinit var tvStock: TextView
        @BindView(R.id.btn_add_cart)
        lateinit var btnAddToCart: AppCompatImageButton
        @BindView(R.id.btn_more)
        lateinit var btnMore: AppCompatImageButton
        @BindView(R.id.nb_quantity)
        lateinit var nbQantity : NumberButton

        init {
            ButterKnife.bind(this, itemView)
        }

    }

    fun updateAdapter(products: List<ProductWithStock>) {
        this.products = products
        notifyDataSetChanged()
    }

    init {
        products = ArrayList()
    }
}