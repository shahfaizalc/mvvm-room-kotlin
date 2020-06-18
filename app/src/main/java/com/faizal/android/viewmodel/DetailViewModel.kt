package com.faizal.android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faizal.android.database.ClubRepository
import com.faizal.android.model.ClubsModel

/**
 * View Model to keep a reference to the club repository.
 */
class DetailViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val mRepository: ClubRepository = ClubRepository(application)
    var getClubs: LiveData<ClubsModel>
    fun fetchClub(query: String?) {
        getClubs = mRepository.getClub(query)
    }

    init {
        getClubs = MutableLiveData()
    }
}