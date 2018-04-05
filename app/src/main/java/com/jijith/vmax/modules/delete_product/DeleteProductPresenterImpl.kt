package com.jijith.vmax.modules.delete_product

import android.content.Context
import com.jijith.vmax.R
import com.jijith.vmax.adapter.DeleteProductAdapter
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.models.Product
import com.jijith.vmax.utils.AppLog
import javax.inject.Inject
import io.reactivex.disposables.Disposable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.nio.file.Files.delete
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer


/**
 * Created by jijith on 12/25/17.
 */
class DeleteProductPresenterImpl @Inject constructor(private var context: DeleteProductActivity, private var deleteProductView: DeleteProductView,
                                                     private var apiService: ApiService, private var appDatabase: AppDatabase) : DeleteProductPresenter {

    companion object {
        val TAG : String = DeleteProductPresenterImpl::class.java.simpleName;
    }

    lateinit var deleteProductAdapter: DeleteProductAdapter

    override fun deleteProduct(product: Product) {

        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                appDatabase.productModel().deleteProduct(product)
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onComplete() {
                deleteProductView.onProductDeleted(context.getString(R.string.delete_product_success))

            }

            override fun onError(e: Throwable) {
                AppLog.e(TAG, e.toString())
            }
        })
    }

    override fun getProducts(context: Context) {
        deleteProductAdapter = DeleteProductAdapter(context)

        appDatabase.productModel().getAllProductItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<List<Product>> {
                    override fun accept(t: List<Product>) {
                        deleteProductAdapter.updateAdapter(t)
                    }
                })

        AppLog.e("Size", "" + deleteProductAdapter.itemCount)

        deleteProductView.setAdapter(deleteProductAdapter)
    }

    override fun updateAdapter(context: Context) {

        appDatabase.productModel().getAllProductItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<List<Product>> {
                    override fun accept(t: List<Product>) {
                        deleteProductAdapter.updateAdapter(t)
                    }
                })

        AppLog.e("Size", "" + deleteProductAdapter.itemCount)
    }
}