package com.jijith.vmax.modules.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.jijith.vmax.R
import com.jijith.vmax.base.BaseActivity
import com.jijith.vmax.modules.home.MainActivity
import com.jijith.vmax.utils.AppLog
import com.jijith.vmax.utils.Constants
import com.jijith.vmax.utils.Utils
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    private val TAG = LoginActivity::class.java.simpleName
    private val REQUEST_PERMISSION = 100

    @BindView(R.id.edt_username) lateinit var mUserName: TextInputEditText
    @BindView(R.id.edt_password) lateinit var mPassword: TextInputEditText

    @BindView(R.id.til_username) lateinit var mLayoutUserName: TextInputLayout
    @BindView(R.id.til_password) lateinit var mLayoutPassword: TextInputLayout
//    @BindView(R.id.btn_login) lateinit var m : TextInputEditText

    @Inject lateinit var loginPresenter: LoginPresenter

    override fun onErrorUserName(msg: String) {
        mLayoutUserName.error = msg
    }

    override fun onErrorPassword(msg: String) {
        mLayoutPassword.error = msg
    }

    override fun onLoginSuccess(msg: String) {
        Utils.Toast(this, msg)
        Utils.SavePreferences(this, Constants.LOGEDIN, true)
        var intentMainActivity = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentMainActivity)

    }

    override fun onLoginFailed(msg: String) {
        Utils.Toast(this, msg)
    }


    override fun getLayout(): Int {
        return R.layout.activity_login;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login),
        AndroidInjection.inject(this);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // Storage permission has not been granted.
                requestPermission(REQUEST_PERMISSION)
            } else
                Utils.hasAppFolder(this)
        } else
            Utils.hasAppFolder(this)
    }

    @OnClick(R.id.btn_login)
    fun onClickLogin() {
        loginPresenter.loadLogin(mUserName.text.toString(), mPassword.text.toString())
    }

    private fun requestPermission(id: Int) {
        AppLog.d(TAG, "Storage permission has NOT been granted. Requesting permission.")

        // Storage permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.WRITE_SETTINGS), id)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == REQUEST_PERMISSION) {

            if (grantResults.size == 3
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Utils.hasAppFolder(this)
            } else if (grantResults.size == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Utils.hasAppFolder(this)
            } else {
                Toast.makeText(this, "Please allow permission to continue.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
