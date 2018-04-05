package com.jijith.vmax.base

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by jijith on 12/26/17.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
        AppModule::class, ActivityBuilder::class))
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: Vmax)

}