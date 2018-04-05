package com.jijith.vmax.modules.login

import com.jijith.vmax.api.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module (includes = arrayOf(Details::class))
 class LoginActivityModule {

    @Provides
    fun provideLoginPresenter(loginActivity: LoginActivity, loginView: LoginView, apiService: ApiService): LoginPresenter {
        return LoginPresenterImpl(loginActivity, loginView, apiService)
    }

}

@Module
interface Details {

    @Binds
    fun provideLoginView(loginActivity: LoginActivity): LoginView

}