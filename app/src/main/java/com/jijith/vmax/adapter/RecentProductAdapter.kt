package com.jijith.vmax.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.vmax.R
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.Stock
import com.jijith.vmax.utils.AppLog
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.io.File

/**
 * Created by jijith on 1/24/18.
 */
class RecentProductAdapter : RecyclerView.Adapter<RecentProductAdapter.ViewHolder> {

    var context : Context? = null
    var products : List<Product>
    var stocks : List<Stock>

    constructor(context: Context?) : super() {
        this.context = context
        products = ArrayList<Product>()
        stocks = ArrayList<Stock>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(context, R.layout.layout_recent_product_adapter, null)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val uri = Uri.fromFile(File(products.get(position).imagePath))
        Picasso.with(context).invalidate(uri);
        Picasso.with(context)
                .load(uri)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder?.ivProduct)

        holder?.tvProductName?.text = products.get(position).productName


        var tempStock = Stock()

        for (stock in stocks){
            if (stock.productId == products.get(position).id) {
                if (tempStock.id == 0) {
                    tempStock = stock
                    break
                }
            }
        }

        holder?.tvPrice?.text = context?.getString(R.string.txt_rs) + tempStock.salePrice

//        holder?.tvStock?.text = context?.getString(R.string.txt_stock) +
//                (products.get(position).quantity - products.get(position).remainingStock)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_product) lateinit var ivProduct: ImageView
        @BindView(R.id.tv_product_name) lateinit var tvProductName: TextView
        @BindView(R.id.tv_price) lateinit var tvPrice: TextView
        @BindView(R.id.tv_stock) lateinit var tvStock: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }

    fun updateAdapter(products: List<Product>, stocks : List<Stock>) {
        this.products = products
        this.stocks = stocks
        notifyDataSetChanged()
    }

}