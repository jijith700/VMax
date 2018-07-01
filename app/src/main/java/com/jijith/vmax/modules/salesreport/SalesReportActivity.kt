package com.jijith.vmax.modules.salesreport

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.jijith.vmax.R
import com.jijith.vmax.adapter.SalesReportAdapter
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.modules.add_product.AddProductActivity
import com.jijith.vmax.utils.Constants
import com.jijith.vmax.utils.TimestampConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SalesReportActivity : BaseActivity(), SalesReportView {

    @BindView(R.id.tv_from_date)
    lateinit var startDate: TextView
    @BindView(R.id.tv_to_date)
    lateinit var endDate: TextView
    @BindView(R.id.rv_sales_report)
    lateinit var salesList: RecyclerView

    private var fromDatePicker: DatePickerDialog? = null
    private var toDatePicker: DatePickerDialog? = null
    private var fromDate: Date? = null
    private var toDate: Date? = null

    @Inject
    lateinit var salesReportPresenter: SalesReportPresenter

    override fun getLayout(): Int {
        return R.layout.activity_sales_report
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val fromDate = year.toString() + "-" + month + "-" + 1
        val toDate = year.toString() + "-" + month + "-" + day

        this.fromDate = TimestampConverter().fromTimestamp(fromDate)
        this.toDate = calendar.time
        fromDatePicker = DatePickerDialog(this, fromDateListener, year, month, day)

        val fromMillis = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
                .parse("2018/01/01 00:00:00").time

        try {
            fromDatePicker?.datePicker?.maxDate = Calendar.getInstance().timeInMillis
            fromDatePicker?.datePicker?.minDate = fromMillis
        } catch (e: ParseException) {
            Log.e(AddProductActivity.TAG, e.toString())
        }

        toDatePicker = DatePickerDialog(this, toDateListener, year, month, day)

        val toMillis = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
                .parse("2018/01/01 00:00:00").time

        try {
            toDatePicker?.datePicker?.maxDate = Calendar.getInstance().timeInMillis
            toDatePicker?.datePicker?.minDate = toMillis
        } catch (e: ParseException) {
            Log.e(AddProductActivity.TAG, e.toString())
        }

        startDate.text = fromDate
        endDate.text = toDate

        salesReportPresenter.getSalesReport(this, this.fromDate!!, this.toDate!!)
    }

    override fun setAdapter(adapter: SalesReportAdapter) {
//        val mLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity, VERTICAL, false)
        val mLayoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(1, 1)
        salesList.layoutManager = mLayoutManager
        salesList.itemAnimator = DefaultItemAnimator()
        salesList.adapter = adapter
    }


    private val fromDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        var month = arg2
        // arg1 = year
        // arg2 = month
        // arg3 = day
        Log.e("fromDate", arg1.toString() + " " + month + " " + arg3)

        month += 1
        val date = arg1.toString() + "-" + month + "-" + arg3

        try {
            fromDate = SimpleDateFormat(Constants.TIME_STAM_FORMAT, Locale.getDefault()).parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        try {
            toDatePicker?.datePicker?.maxDate = Calendar.getInstance().timeInMillis
            toDatePicker?.datePicker?.minDate = fromDate!!.time
        } catch (e: ParseException) {
            Log.e(AddProductActivity.TAG, e.toString())
        }

        startDate.text = date
        startDate.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))

        salesReportPresenter.getSalesReport(this, fromDate!!, toDate!!)

    }

    private val toDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        var month = arg2
        // arg1 = year
        // month = month
        // arg3 = day
        Log.e("fromDate", arg1.toString() + " " + month + " " + arg3)

        month += 1
        val date = arg1.toString() + "-" + month + "-" + arg3

        try {
            toDate = SimpleDateFormat(Constants.TIME_STAM_FORMAT, Locale.getDefault()).parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        try {
            fromDatePicker?.datePicker?.maxDate = Calendar.getInstance().timeInMillis
            fromDatePicker?.datePicker?.minDate = toDate!!.time
        } catch (e: ParseException) {
            Log.e(AddProductActivity.TAG, e.toString())
        }

        endDate.text = date
        endDate.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))

        salesReportPresenter.getSalesReport(this, fromDate!!, toDate!!)
    }

    @OnClick(R.id.tv_from_date)
    fun onClickStartDate() {
        fromDatePicker?.show()
    }

    @OnClick(R.id.tv_to_date)
    fun onClickEndDate() {
        toDatePicker?.show()
    }
}
