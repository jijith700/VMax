package com.jijith.vmax.modules.edit_product

import android.content.Context
import android.text.TextUtils
import com.jijith.vmax.R
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Product
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
class EditProductPresenterImpl @Inject constructor(private var context: EditProductActivity, private var editProductView: EditProductView,
                                                   private var apiService: ApiService, private var appDatabase: AppDatabase) : EditProductPresenter {

    companion object {
        val TAG : String = EditProductPresenterImpl::class.java.simpleName;
    }


    override fun editProduct(product: Product, fileName: String) {

        if (isValid(product.productName, fileName)) {
            if (Utils.hasAppFolder(context) && !fileName.equals(product.imagePath)) {
                val path = File(Utils.getFolderPath(context, context.getString(R.string.dir_product)).toString() + File.separator + product.productName + ".jpg")

//                deleteFile(File(product.imagePath))
                copyFile(File(fileName), path)

                product.imagePath = path.toString()
            }

            Completable.fromAction {
                appDatabase.productModel().addProduct(product)
            }.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                        override fun onSubscribe(d: Disposable) {}

                        override fun onComplete() {
                            editProductView.onProductUpdated(context.getString(R.string.update_product_success))
                        }

                        override fun onError(e: Throwable) {
                            AppLog.e(TAG, e.toString())
                        }
                    })
        }
    }

    override fun getProducts(context: Context) {
//        deleteProductAdapter.updateAdapter(realm.where(Product::class.java).findAll().toList())

//        AppLog.e("Size", "" + deleteProductAdapter.itemCount)

    }

    private fun isValid(productName: String, imagePath: String): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(imagePath)) {
            editProductView.onErrorProductImage(context.getString(R.string.error_image))
            isValid = false
        }

        if (TextUtils.isEmpty(productName)) {
            editProductView.onErrorStockName(context.getString(R.string.error_field))
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
            AppLog.e(AddProductPresenterImpl.TAG, "Copy file successful.")
        } finally {
            if (source != null) {
                source.close()
            }
            if (destination != null) {
                destination.close()
            }
        }
    }

    private fun deleteFile(path: File) {
        path.delete()
    }
}