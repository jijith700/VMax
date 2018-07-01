package com.jijith.vmax.modules.checkout

import com.jijith.vmax.R
import com.jijith.vmax.adapter.CheckOutAdapter
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Invoice
import com.jijith.vmax.models.ProductWithStock
import com.jijith.vmax.models.Sales
import com.jijith.vmax.models.Stock
import com.jijith.vmax.modules.add_product.AddProductPresenterImpl
import com.jijith.vmax.utils.AppLog
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Created by jijith on 12/25/17.
 */
class CheckOutPresenterImpl @Inject constructor(private var context: CheckOutActivity, private var checkOutView: CheckOutView,
                                                private var apiService: ApiService, private var appDatabase: AppDatabase) : CheckOutPresenter {


    companion object {
        val TAG: String = CheckOutPresenterImpl::class.java.simpleName;
    }

    lateinit var checkOutAdapter: CheckOutAdapter

    var maxInvoiceId = 0
    var maxSalesId = 0

    override fun getId() {

        appDatabase.invoiceModel().getItemId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleReturnInvoiceId, this::errorInvoiceId)


        appDatabase.salesModel().getItemId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleReturnSalesId, this::errorSalesId)

    }

    override fun setAdapter(productWithStock: ArrayList<ProductWithStock>) {
        checkOutAdapter = CheckOutAdapter(this, context, productWithStock)
        checkOutView.setAdapter(checkOutAdapter)
        setTotalPrice(productWithStock)
    }

    override fun setTotalPrice() {
        val products = checkOutAdapter.getProducts()
        var totalPrice = 0
        for (product in products) {
            totalPrice += product.totalPrice
        }

        checkOutView.onChangeTotalPrice(totalPrice)
    }

    fun setTotalPrice(products: ArrayList<ProductWithStock>) {
        var totalPrice = 0
        for (product in products) {
            totalPrice += product.stock!![product.stock!!.size - 1].salePrice * product.count
        }

        checkOutView.onChangeTotalPrice(totalPrice)
    }

    override fun checkOut(name: String, phone: String, discount: Int, commissionPaidTo: String,
                          commission: Int, purchaseMode: Int) {

        if (isValid(name, phone) && checkOutAdapter.getProducts().isNotEmpty()) {
            val products = checkOutAdapter.getProducts()

            var totalPrice = 0
            for (product in products) {
                totalPrice += product.totalPrice
            }

            val netAmount = totalPrice - (discount + commission)
            val invoice = Invoice(++maxInvoiceId, name, phone, totalPrice,
                    netAmount, commissionPaidTo, commission, discount, purchaseMode)

            var salesList: List<Sales> = ArrayList()
            val calendar = Calendar.getInstance()


            for (product in products) {

                var remaining = 0
                for (stock in product.stock!!) {
                    if (product.count > stock.balanceStock) {
                        val sales = Sales(++maxSalesId, product.product!!.id, stock.id,
                                product.product!!.productName, calendar.time, stock.quantity,
                                product.stock!![product.stock!!.size - 1].salePrice, product.totalPrice)

                        salesList += sales
                        remaining = product.count - stock.balanceStock

                        stock.balanceStock = 0
                        stock.isClosed = true

                        updateStock(stock)


                    } else {
                        if (remaining == 0) {
                            val sales = Sales(++maxSalesId, product.product!!.id, stock.id,
                                    product.product!!.productName, calendar.time, stock.quantity,
                                    product.stock!![product.stock!!.size - 1].salePrice, product.totalPrice)
                            salesList += sales

                            stock.balanceStock -= product.count
                            if (stock.balanceStock == 0)
                                stock.isClosed = true

                            updateStock(stock)

                            break
                        } else {
                            val sales = Sales(++maxSalesId, product.product!!.id, stock.id,
                                    product.product!!.productName, calendar.time, remaining,
                                    product.stock!![product.stock!!.size - 1].salePrice, product.totalPrice)
                            salesList += sales

                            stock.balanceStock -= product.count
                            if (stock.balanceStock == 0)
                                stock.isClosed = true

                            updateStock(stock)

                            break
                        }
                    }
                }
            }

            Completable.fromAction {
                appDatabase.invoiceModel().addInvoice(invoice)
                appDatabase.salesModel().addSales(salesList)
            }.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                        override fun onSubscribe(d: Disposable) {}

                        override fun onComplete() {
                            checkOutView.onCompleteCheckOut()
                        }

                        override fun onError(e: Throwable) {
                            AppLog.e(AddProductPresenterImpl.TAG, e.toString())
                        }
                    })

        }
    }

    fun isValid(name: String, phone: String): Boolean {
        var isValid = true
        if (name.isBlank()) {
            isValid = false
            checkOutView.onErrorName(context.getString(R.string.error_name))
        }

        return isValid
    }

    fun updateStock(stock: Stock) {
        Completable.fromAction {
            appDatabase.stockModel().update(stock)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        AppLog.d(TAG, "Stock updated...")
                    }

                    override fun onError(e: Throwable) {
                        AppLog.e(AddProductPresenterImpl.TAG, e.toString())
                    }
                })
    }

    /**
     * copy file
     *
     * @param sourceFile source file
     * @param destFile   destination file
     */
    @Throws(IOException::class)
    private fun copyFile(sourceFile: File, destFile: File?) {
        var source: FileChannel? = null
        var destination: FileChannel? = null
        try {
            if (!destFile!!.exists()) {
                destFile.createNewFile()
            }

            source = FileInputStream(sourceFile).getChannel()
            destination = FileOutputStream(destFile).getChannel()
            destination!!.transferFrom(source, 0, source!!.size())
            AppLog.e(TAG, "Copy file successful.")
        } finally {
            if (source != null) {
                source.close()
            }
            if (destination != null) {
                destination.close()
            }
        }
    }


//        val request = JSONObject()
//        try {
//            request.accumulate(Constants.KEY_USERNAME, userName)
//            request.accumulate(Constants.KEY_PASSWORD, password)
//
//        } catch (e: JSONException) {
//            AppLog.e(TAG, e.toString())
//        }
//
//
//        AppLog.d(TAG, request.toString())
//        val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), request.toString())
//
//
//        apiService.getRetrofit().login(requestBody)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(object : DisposableObserver<Response<ResponseBody>>() {
//                    override fun onComplete() {
//                        AppLog.e(TAG, "Complete")
//                    }
//
//                    override fun onNext(t: Response<ResponseBody>) {
//                        if (t.code() == 200)
//                            AppLog.e(TAG, "success")
//                        else
//                            AppLog.e(TAG, "failed")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        AppLog.e(TAG, e.toString());
//                    }
//                });


    fun handleReturnInvoiceId(id: Int) {
        maxInvoiceId = id;
        AppLog.d(TAG, "Got Id " + id)
    }

    fun errorInvoiceId(error: Throwable) {
        maxInvoiceId = 0;
        AppLog.d(TAG, "Got " + error)
    }

    fun handleReturnSalesId(id: Int) {
        maxSalesId = id;
        AppLog.d(TAG, "Got Id " + id)
    }

    fun errorSalesId(error: Throwable) {
        maxSalesId = 0
        AppLog.d(TAG, "Got " + error)
    }
}