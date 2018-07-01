package com.jijith.vmax.modules.salesreport

import android.content.Context
import java.util.*

/**
 * Created by jijith on 12/25/17.
 */

interface SalesReportPresenter {

    fun getSalesReport(context: Context, fromDate: Date, toDate: Date)

}