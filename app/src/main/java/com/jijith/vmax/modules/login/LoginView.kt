package com.jijith.vmax.modules.login

/**
 * Created by jijith on 12/25/17.
 */
interface LoginView {
    fun onLoginSuccess(msg : String)
    fun onLoginFailed(msg : String)
    fun onErrorUserName(msg: String)
    fun onErrorPassword(msg: String)
}