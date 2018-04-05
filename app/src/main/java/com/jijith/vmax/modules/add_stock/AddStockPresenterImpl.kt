package com.jijith.vmax.modules.add_stock

import android.text.TextUtils
import com.jijith.vmax.R
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.Stock
import com.jijith.vmax.modules.add_product.AddProductPresenterImpl
import com.jijith.vmax.utils.AppLog
import com.jijith.vmax.utils.Utils
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
import javax.inject.Inject


/**
 * Created by jijith on 12/25/17.
 */
class AddStockPresenterImpl @Inject constructor(private var context: AddStockActivity, private var addStockView: AddStockView,
                                                private var apiService: ApiService, private var appDatabase: AppDatabase) : AddStockPresenter {

    companion object {
        val TAG: String = AddStockPresenterImpl::class.java.simpleName;
    }

    var maxProductId = 0
    var maxStockId = 0

    override fun getId() {

        appDatabase.stockModel().getItemId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleReturnStockId, this::errorStockId)

    }

    override fun addStock(product: Product, stock: Stock) {
        if (isValid(product.productName, stock.purchaseDate, product.quantity,
                        stock.stockPrice, stock.salePrice, product.imagePath)) {
            if (Utils.hasAppFolder(context)) {
                val path = File(Utils.getFolderPath(context, context.getString(R.string.dir_product)).toString() + File.separator + product.productName + ".jpg")
                copyFile(File(product.imagePath), path)

                product.imagePath = path.toString()

                stock.quantity = product.quantity
//                stock.stockPrice = product.stockPrice
//                stock.salePrice = product.salePrice
                stock.discount = 0

                val nextStockId = if (maxStockId == null) 1 else maxStockId + 1

                stock.productId = product.id
                stock.id = nextStockId

                Completable.fromAction {
                    appDatabase.productModel().addProduct(product)
                    appDatabase.stockModel().addStock(stock)
                }.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(object : CompletableObserver {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onComplete() {
                                addStockView.onStockAdded(context.getString(R.string.add_stock_success))
                                getId()

                            }

                            override fun onError(e: Throwable) {
                                AppLog.e(AddProductPresenterImpl.TAG, e.toString())
                            }
                        })
            }
        }
    }

    private fun isValid(productName: String, purchaseDate: String, quantity: Int,
                        stockPrice: Int, salePrice: Int, imagePath: String): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(imagePath)) {
            addStockView.onErrorProductImage(context.getString(R.string.error_image))
            isValid = false
        }

        if (TextUtils.isEmpty(productName)) {
            addStockView.onErrorStockName(context.getString(R.string.error_field))
            isValid = false
        }

        if (TextUtils.isEmpty(purchaseDate)) {
            addStockView.onErrorPurchaseDate(context.getString(R.string.error_date))
            isValid = false
        } else if (purchaseDate.equals(context.getString(R.string.stock_date))) {
            addStockView.onErrorPurchaseDate(context.getString(R.string.error_date))
            isValid = false
        }

        if (quantity == 0) {
            addStockView.onErrorQuantity(context.getString(R.string.error_field))
            isValid = false
        }

        if (stockPrice == 0) {
            addStockView.onErrorStockPrice(context.getString(R.string.error_field))
            isValid = false
        }

        if (salePrice == 0) {
            addStockView.onErrorSalePrice(context.getString(R.string.error_field))
            isValid = false
        }

        return isValid
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


    fun handleReturnStockId(id: Int) {
        maxStockId = id;
        AppLog.d(AddProductPresenterImpl.TAG, "Got Id " + id)
    }

    fun errorStockId(error: Throwable) {
        maxStockId = 0
        AppLog.d(AddProductPresenterImpl.TAG, "Got " + error)
    }
}