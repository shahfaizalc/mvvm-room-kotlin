package com.faizal.android.injection


import com.faizal.android.handlers.RecyclerLoadClubsHandler
import com.faizal.android.injection.module.AndroidModule
import com.faizal.android.injection.module.ConstantsModule
import com.faizal.android.injection.module.NetworkStateModule
import com.faizal.android.injection.module.RestHandlerModule
import com.faizal.android.view.DetailedActivity
import com.faizal.android.view.ClubsActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidModule::class, ConstantsModule::class, NetworkStateModule::class, RestHandlerModule::class])
interface ApplicationComponent {
    fun inject(recyclerLoadNewsHandler: RecyclerLoadClubsHandler)
    fun inject(clubsActivity: ClubsActivity)
    fun inject(detailedActivity: DetailedActivity)


}
