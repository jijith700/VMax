package com.jijith.vmax.modules.home

import com.jijith.vmax.api.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Created by jijith on 12/25/17.
 */

@Module
abstract class MainActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideMainPresenter(mainView: MainView, apiService: ApiService): MainPresenter {
            return MainPresenterImpl(mainView, apiService)
        }
    }

    @Binds
    abstract fun provideMainView(loginActivity: MainActivity): MainView

}