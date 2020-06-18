package com.faizal.android.injection.module

import com.faizal.android.communication.RestHandler
import dagger.Module
import dagger.Provides
import javax.inject.Inject


@Module
class RestHandlerModule @Inject constructor() {

    @Provides
    fun provideRestHandler(): RestHandler = RestHandler()

}