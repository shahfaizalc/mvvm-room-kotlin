package com.faizal.android.injection.module

import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ConstantsModule @Inject constructor() {

    @Provides
    fun baseUrl(): String = "https://public.allaboutapps.at/"

    @Provides
    fun subUrl_param(): String = "hiring/clubs.json"

    @Provides
    fun bundleKeyClubName() : String = "Clubname"


}

