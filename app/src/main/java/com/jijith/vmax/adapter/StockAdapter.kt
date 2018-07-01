package com.jijith.vmax.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.vmax.R
import com.jijith.vmax.models.Stock
import com.jijith.vmax.utils.TimestampConverter

/**
 * Created by jijith on 1/26/18.
 */
class StockAdapter(var context: Context?) : RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    private var stocks: List<Stock>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(context, R.layout.layout_stock_adapter, null)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDate.text = TimestampConverter().dateToTime(stocks[holder.adapterPosition].purchaseDate)
        holder.tvStock.text = stocks[holder.adapterPosition].quantity.toString()
        holder.tvBalanceQuantity.text = stocks[holder.adapterPosition].balanceStock.toString()
        holder.tvStockPrice.text = stocks[holder.adapterPosition].stockPrice.toString()
        holder.tvSalePrice.text = stocks[holder.adapterPosition].salePrice.toString()

        holder.btnDelete.setOnClickListener(View.OnClickListener {


        })

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_date)
        lateinit var tvDate: TextView
        @BindView(R.id.tv_stock)
        lateinit var tvStock: TextView
        @BindView(R.id.tv_balance_stock)
        lateinit var tvBalanceQuantity: TextView
        @BindView(R.id.tv_stock_price)
        lateinit var tvStockPrice: TextView
        @BindView(R.id.tv_sale_price)
        lateinit var tvSalePrice: TextView
        @BindView(R.id.btn_delete)
        lateinit var btnDelete: AppCompatImageButton

        init {
            ButterKnife.bind(this, itemView)
        }

    }

    fun updateAdapter(stocks: List<Stock>) {
        this.stocks = stocks
        notifyDataSetChanged()
    }

    init {
        stocks = ArrayList<Stock>()
    }
}