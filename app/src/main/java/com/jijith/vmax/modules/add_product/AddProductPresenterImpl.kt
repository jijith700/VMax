package com.jijith.vmax.modules.add_product

import android.text.TextUtils
import com.jijith.vmax.R
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Product
import com.jijith.vmax.models.Stock
import com.jijith.vmax.utils.AppLog
import com.jijith.vmax.utils.Utils
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import javax.inject.Inject
import io.reactivex.disposables.Disposable


/**
 * Created by jijith on 12/25/17.
 */
class AddProductPresenterImpl @Inject constructor(private var context: AddProductActivity, private var addProductView: AddProductView,
                                                  private var apiService: ApiService, private var appDatabase: AppDatabase) : AddProductPresenter {


    companion object {
        val TAG: String = AddProductPresenterImpl::class.java.simpleName;
    }

    var maxProductId = 0
    var maxStockId = 0

    override fun getId() {

        appDatabase.productModel().getItemId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleReturnProductId, this::errorProductId)


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
                stock.balanceStock = product.quantity
                stock.discount = 0

                val nextProductId = if (maxProductId == null) 1 else maxProductId + 1
                val nextStockId = if (maxStockId == null) 1 else maxStockId + 1

                product.id = nextProductId
                stock.productId = nextProductId
                stock.id = nextStockId

                Completable.fromAction {
                    appDatabase.productModel().addProduct(product)
                    appDatabase.stockModel().addStock(stock)
                }.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onComplete() {
                                addProductView.onStockAdded(context.getString(R.string.add_product_success))
                                getId()

                            }

                            override fun onError(e: Throwable) {
                                AppLog.e(TAG, e.toString())
                            }
                        })
            }
        }
    }

    private fun isValid(productName: String, purchaseDate: String, quantity: Int,
                        stockPrice: Int, salePrice: Int, imagePath: String): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(imagePath)) {
            addProductView.onErrorProductImage(context.getString(R.string.error_image))
            isValid = false
        }

        if (TextUtils.isEmpty(productName)) {
            addProductView.onErrorStockName(context.getString(R.string.error_field))
            isValid = false
        }

        if (TextUtils.isEmpty(purchaseDate)) {
            addProductView.onErrorPurchaseDate(context.getString(R.string.error_date))
            isValid = false
        } else if (purchaseDate.equals(context.getString(R.string.stock_date))) {
            addProductView.onErrorPurchaseDate(context.getString(R.string.error_date))
            isValid = false
        }

        if (quantity == 0) {
            addProductView.onErrorQuantity(context.getString(R.string.error_field))
            isValid = false
        }

        if (stockPrice == 0) {
            addProductView.onErrorStockPrice(context.getString(R.string.error_field))
            isValid = false
        }

        if (salePrice == 0) {
            addProductView.onErrorSalePrice(context.getString(R.string.error_field))
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


    fun handleReturnProductId(id: Int) {
        maxProductId = id;
        AppLog.d(TAG, "Got Id " + id)
    }

    fun errorProductId(error: Throwable) {
        maxProductId = 0;
        AppLog.d(TAG, "Got " + error)
    }

    fun handleReturnStockId(id: Int) {
        maxStockId = id;
        AppLog.d(TAG, "Got Id " + id)
    }

    fun errorStockId(error: Throwable) {
        maxStockId = 0
        AppLog.d(TAG, "Got " + error)
    }
}