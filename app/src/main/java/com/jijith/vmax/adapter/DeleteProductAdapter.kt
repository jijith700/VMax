package com.jijith.vmax.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.vmax.R
import com.jijith.vmax.models.Product
import com.jijith.vmax.modules.delete_product.DeleteProductActivity
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.io.File

/**
 * Created by jijith on 1/26/18.
 */
class DeleteProductAdapter : RecyclerView.Adapter<DeleteProductAdapter.ViewHolder> {

    var context: Context? = null
    var products: List<Product>

    constructor(context: Context?) : super() {
        this.context = context
        products = ArrayList<Product>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(context, R.layout.layout_delete_product_adapter, null)

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

//        holder?.tvPrice?.text = context?.getString(R.string.txt_rs) + " " +
//                products.get(position).salePrice

        holder?.btnDelete?.setOnClickListener(View.OnClickListener {

            (context as DeleteProductActivity).deleteProduct(products.get(position))

        })

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_product)
        lateinit var ivProduct: ImageView
        @BindView(R.id.tv_product_name)
        lateinit var tvProductName: TextView
        @BindView(R.id.tv_price)
        lateinit var tvPrice: TextView
        @BindView(R.id.btn_delete)
        lateinit var btnDelete: AppCompatImageButton

        init {
            ButterKnife.bind(this, itemView)
        }

    }

    fun updateAdapter(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}