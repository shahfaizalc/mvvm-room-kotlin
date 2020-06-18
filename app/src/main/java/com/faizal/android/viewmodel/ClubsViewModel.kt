package com.faizal.android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faizal.android.adapter.ClubsListAdapter
import com.faizal.android.database.ClubRepository
import com.faizal.android.model.ClubsModel
import java.util.function.Consumer

/**
 * View Model to keep a reference to the club repository and
 * an up-to-date list of all clubs.
 */
class ClubsViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val mRepository: ClubRepository = ClubRepository(application)
    val allClubs: LiveData<List<ClubsModel>>
    var clickedItem: MutableLiveData<String>
    var adapter: ClubsListAdapter? = null
    var progressBarVisible: MutableLiveData<Int>
    var msgView: MutableLiveData<Int>
    fun setListAdapter(adapter1: ClubsListAdapter?) {
        adapter = adapter1
    }

    fun insertAll(iterator: Iterable<ClubsModel?>) {
        iterator.forEach(Consumer { clubsModel: ClubsModel? -> insert(clubsModel) })
    }

    fun insert(clubsModel: ClubsModel?) {
        mRepository.insert(clubsModel)
    }

    fun launchDetailed(itemId: String) {
        clickedItem.postValue(itemId)
    }

    fun getClickedItem(): LiveData<String> {
        return clickedItem
    }

    fun setProgressBarVisible(progressBarVisible: Int) {
        this.progressBarVisible.postValue(progressBarVisible)
    }

    val progressVisible: LiveData<Int>
        get() = progressBarVisible

    fun setMsgView(itemId: Int) {
        msgView.postValue(itemId)
    }

    fun getMsgView(): LiveData<Int> {
        return msgView
    }

    init {
        allClubs = mRepository.allClubs
        clickedItem = MutableLiveData()
        progressBarVisible = MutableLiveData()
        msgView = MutableLiveData()
    }
}