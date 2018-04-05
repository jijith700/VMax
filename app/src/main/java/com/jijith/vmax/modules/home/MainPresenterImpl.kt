package com.jijith.vmax.modules.home

import android.text.TextUtils
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.utils.Utils
import javax.inject.Inject

/**
 * Created by jijith on 12/25/17.
 */
class MainPresenterImpl @Inject constructor(internal var mainView: MainView, internal var apiService: ApiService) : MainPresenter {

    var TAG : String = MainPresenterImpl::class.java.simpleName;


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
}