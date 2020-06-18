package com.faizal.android.injection.module

import com.faizal.android.utils.NetworkStateHandler
import dagger.Module
import dagger.Provides
import javax.inject.Inject


@Module
open class NetworkStateModule @Inject constructor() {

    @Provides
    fun networkStateHandler(): NetworkStateHandler = NetworkStateHandler()

}