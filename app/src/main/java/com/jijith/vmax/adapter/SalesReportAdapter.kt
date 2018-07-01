package com.jijith.vmax.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jijith.vmax.R
import com.jijith.vmax.models.Sales
import com.jijith.vmax.utils.TimestampConverter


/**
 * Created by jijith
 */
class SalesReportAdapter(var context: Context?) : RecyclerView.Adapter<SalesReportAdapter.ViewHolder>() {


    var sales: List<Sales>

    init {
        sales = ArrayList()
    }


    override fun getItemCount(): Int {
        return sales.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCustomer.text = sales[holder.adapterPosition].productName
        holder.tvPrice.text = String.format(context!!.getString(R.string.txt_rs), sales[holder.adapterPosition].salePrice)
        holder.tvDate.text = TimestampConverter().dateToTime(sales[holder.adapterPosition].saleDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.layout_sales_report_adapter, null)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_product)
        lateinit var tvCustomer: TextView
        @BindView(R.id.tv_price)
        lateinit var tvPrice: TextView
        @BindView(R.id.tv_date)
        lateinit var tvDate: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    fun updateAdapter(sales: List<Sales>) {
        this.sales = sales
        notifyDataSetChanged()
    }
}