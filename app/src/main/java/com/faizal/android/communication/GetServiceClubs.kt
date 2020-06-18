package com.faizal.android.communication

import com.faizal.android.model.ClubsModel
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface GetServiceClubs {

    @GET
    fun retrieveRepositoriesAsync(@Url url:String): Deferred<List<ClubsModel>>

}