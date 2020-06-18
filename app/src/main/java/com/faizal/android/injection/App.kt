package com.faizal.android.injection

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.pm.PackageManager
import android.os.Bundle
import com.faizal.android.database.R
import com.faizal.android.injection.ApplicationComponent
import com.faizal.android.injection.DaggerApplicationComponent
import com.faizal.android.injection.module.AndroidModule
import com.faizal.android.injection.module.ConstantsModule
import com.faizal.android.injection.module.NetworkStateModule
import com.faizal.android.injection.module.RestHandlerModule

class App : Application() {

    companion object {
        @JvmStatic
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .networkStateModule(NetworkStateModule())
                .restHandlerModule(RestHandlerModule())
                .constantsModule(ConstantsModule())
                .build()
    }
}

