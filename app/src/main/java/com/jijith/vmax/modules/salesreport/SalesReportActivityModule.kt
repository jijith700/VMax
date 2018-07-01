package com.jijith.vmax.modules.salesreport

import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
abstract class SalesReportActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideSalesReportPresenter(salesReportActivity: SalesReportActivity, salesReportView: SalesReportView,
                                        apiService: ApiService, appDatabase: AppDatabase): SalesReportPresenter {
            return SalesReportPresenterImpl(salesReportActivity, salesReportView, apiService, appDatabase)
        }
    }

    @Binds
    abstract fun provideSalesReportView(salesReportActivity: SalesReportActivity): SalesReportView


}