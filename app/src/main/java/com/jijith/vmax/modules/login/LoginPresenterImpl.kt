package com.jijith.vmax.modules.login

import android.text.TextUtils
import com.jijith.vmax.R
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.utils.Utils
import javax.inject.Inject

/**
 * Created by jijith on 12/25/17.
 */
class LoginPresenterImpl @Inject constructor(internal var context: LoginActivity, internal var loginView: LoginView,
                                             internal var apiService: ApiService) : LoginPresenter {

    var TAG : String = LoginPresenterImpl::class.java.simpleName;

    override fun loadLogin(userName : String, password : String ) {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            if (userName.equals("vmax") && password.equals("vipin")) {

//                apiService.getRetrofit().login()
                loginView.onLoginSuccess("Login Success")
            } else
                loginView.onLoginFailed(context.getString(R.string.error_login))
        } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
            loginView.onErrorUserName(context.getString(R.string.error_empty))
            loginView.onErrorPassword(context.getString(R.string.error_empty))
        } else if (TextUtils.isEmpty(userName)) {
            loginView.onErrorUserName(context.getString(R.string.error_empty))
        } else if (TextUtils.isEmpty(password)) {
            loginView.onErrorPassword(context.getString(R.string.error_empty))
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
}