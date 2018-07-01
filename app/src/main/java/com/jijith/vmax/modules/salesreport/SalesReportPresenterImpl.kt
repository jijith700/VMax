package com.jijith.vmax.modules.salesreport

import android.content.Context
import com.jijith.vmax.adapter.SalesReportAdapter
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Sales
import com.jijith.vmax.utils.AppLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


/**
 * Created by jijith on 12/25/17.
 */
class SalesReportPresenterImpl @Inject constructor(private var context: SalesReportActivity, private var salesReportView: SalesReportView,
                                                   private var apiService: ApiService, private var appDatabase: AppDatabase) : SalesReportPresenter {

    companion object {
        val TAG: String = SalesReportPresenterImpl::class.java.simpleName;
    }

    lateinit var salesReportAdapter: SalesReportAdapter

    override fun getSalesReport(context: Context, fromDate: Date, toDate: Date) {
        salesReportAdapter = SalesReportAdapter(context);
        appDatabase.salesModel().getSalesByDate(fromDate, toDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t -> salesReportAdapter.updateAdapter(t) }

        salesReportView.setAdapter(salesReportAdapter)

        AppLog.e("Size", "" + salesReportAdapter.itemCount)

    }
}