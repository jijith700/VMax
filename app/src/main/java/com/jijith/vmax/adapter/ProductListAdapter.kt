package com.jijith.vmax.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.vmax.R
import com.jijith.vmax.models.Product
import com.jijith.vmax.modules.delete_product.DeleteProductActivity
import com.jijith.vmax.modules.edit_product.EditProductActivity
import com.jijith.vmax.utils.Constants
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import org.parceler.Parcels
import java.io.File

/**
 * Created by jijith on 1/26/18.
 */
class ProductListAdapter(var context: Context?) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var products: List<Product>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(context, R.layout.layout_product_list_adapter, null)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val uri = Uri.fromFile(File(products.get(position).imagePath))
//        Picasso.with(context).invalidate(uri);
//        Picasso.with(context)
//                .load(uri)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .into(holder?.ivProduct)
//
//        holder?.tvProductName?.text = products.get(position).productName
//
//        holder?.tvPrice?.text = context?.getString(R.string.txt_rs) + " " +
//                products.get(position).salePrice

//        holder?.btnDelete?.setOnClickListener(View.OnClickListener {
//
//            (context as DeleteProductActivity).deleteProduct(products.get(position))
//
//        })

        holder?.itemView?.setOnClickListener(View.OnClickListener {
            val popup = PopupMenu(context, holder.itemView)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.product_popup_menu, popup.menu)

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener { item ->
                //                        Toast.makeText(context,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                if (item.itemId == R.id.menu_cart) {

                } else if (item.itemId == R.id.menu_edit) {
                    val intentEdit = Intent(context, EditProductActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(Constants.PRODUCT, Parcels.wrap(products.get(position)));
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
        @BindView(R.id.btn_delete)
        lateinit var btnDelete: Button

        init {
            ButterKnife.bind(this, itemView)
        }

    }

    fun updateAdapter(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    init {
        products = ArrayList<Product>()
    }
}