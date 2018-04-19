package com.jijith.vmax.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.numberbutton.NumberButton
import com.jijith.vmax.R
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.modules.chekout.CheckOutPresenterImpl
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.io.File
import android.content.ClipData.Item


/**
 * Created by jijith on 1/26/18.
 */
class CheckOutAdapter(var context: Context?) : RecyclerView.Adapter<CheckOutAdapter.ViewHolder>() {

    private var products: ArrayList<ProductWithStock>
    private var checkOutPresenter: CheckOutPresenterImpl? = null

    constructor(checkOutPresenter: CheckOutPresenterImpl, context: Context?, products: ArrayList<ProductWithStock>) : this(context) {
        this.checkOutPresenter = checkOutPresenter
        this.products = products
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(context, R.layout.layout_check_out_adapter, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val uri = Uri.fromFile(File(products[holder.adapterPosition].product?.imagePath))
        Picasso.with(context).invalidate(uri)
        Picasso.with(context)
                .load(uri)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.ivProduct)

        holder.tvProductName.text = products[holder.adapterPosition].product?.productName

        if (products[holder.adapterPosition].stock!!.size > 1) {

            var stock = 0
            for (i in 0 until products[holder.adapterPosition].stock!!.size - 1) {
                stock += products[holder.adapterPosition].stock!![i].balanceStock
            }

            if (stock > 100) holder.nbQantity.setRange(1, 100)
            else holder.nbQantity.setRange(1, stock)

        } else {
            if (products[holder.adapterPosition].stock!![0].balanceStock == 0)
                holder.nbQantity.setRange(0, 0)
            else holder.nbQantity.setRange(1, products[holder.adapterPosition].stock!![0].balanceStock)
        }
        holder.nbQantity.setNumber(products[holder.adapterPosition].count)

        holder.edtPricePerUnit.setText(products[holder.adapterPosition].stock!![products[holder.adapterPosition].stock!!.size - 1].salePrice.toString())
        holder.edtPricePerUnit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrBlank()) {
                    products[holder.adapterPosition].stock!![products[holder.adapterPosition].stock!!.size - 1].salePrice = Integer.parseInt(p0!!.toString())

                    val totalPrice = products[holder.adapterPosition].stock!![products[holder.adapterPosition].stock!!.size - 1].salePrice * holder.nbQantity.getIntNumber()
                    holder.tvPrice.text = String.format(context!!.getString(R.string.txt_rs), totalPrice)
                    products[holder.adapterPosition].totalPrice = totalPrice

                    checkOutPresenter!!.setTotalPrice()
                }
            }
        })


        holder.nbQantity.setOnValueChangeListener(object : NumberButton.OnValueChangeListener {
            override fun onValueChange(view: NumberButton, oldValue: Int, newValue: Int) {
                val totalPrice = products[holder.adapterPosition].stock!![products[holder.adapterPosition].stock!!.size - 1].salePrice *
                        holder.nbQantity.getIntNumber()

                holder.tvPrice.text = String.format(context!!.getString(R.string.txt_rs), totalPrice)
                products[holder.adapterPosition].totalPrice = totalPrice

                checkOutPresenter!!.setTotalPrice()
            }
        })

        val totalPrice = products[holder.adapterPosition].stock!![products[holder.adapterPosition].stock!!.size - 1].salePrice *
                holder.nbQantity.getIntNumber()

        holder.tvPrice.text = String.format(context!!.getString(R.string.txt_rs), totalPrice)
        products[holder.adapterPosition].totalPrice = totalPrice

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_product)
        lateinit var ivProduct: ImageView
        @BindView(R.id.tv_product_name)
        lateinit var tvProductName: TextView
        @BindView(R.id.tv_price)
        lateinit var tvPrice: TextView
        @BindView(R.id.edt_price)
        lateinit var edtPricePerUnit: EditText
        @BindView(R.id.nb_quantity)
        lateinit var nbQantity: NumberButton
        @BindView(R.id.layout_foreground)
        lateinit var layoutForeground: RelativeLayout

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    fun updateAdapter(products: ArrayList<ProductWithStock>) {
        this.products = products
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
        checkOutPresenter!!.setTotalPrice()
    }

    fun restoreItem(product: ProductWithStock, position: Int) {
        products.add(position, product)
        notifyItemInserted(position)
        checkOutPresenter!!.setTotalPrice()
    }

    fun getProducts(): ArrayList<ProductWithStock> {
        return products
    }

    init {
        products = ArrayList()
    }
}