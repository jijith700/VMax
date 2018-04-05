package com.jijith.vmax.base

import android.app.Application
import android.content.Context
import com.jijith.vmax.api.ApiService
import com.jijith.vmax.api.WebApiListener
import com.jijith.vmax.database.AppDatabase
import com.jijith.vmax.database.DbHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by jijith on 12/26/17.
 */
@Module
public class AppModule {

//    @Provides
//    @Singleton
//    internal fun provideApplication(): Application {
//        return mApplication
//    }

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application;
    }

//    @Provides
//    @Singleton
//    internal fun provideDatabase(application: Application): DbHelper {
//        return DbHelper(application)
//    }

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Provides
    @Singleton
    internal fun provideApiClient(): WebApiListener {
        return ApiService().getRetrofit()
    }
}