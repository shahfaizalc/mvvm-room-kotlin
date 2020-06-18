package com.faizal.android.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.faizal.android.model.ClubsModel
 /**
 * Abstracted Repository
 */
class ClubRepository(application: Application?) {

    private val mClubDao: ClubDao

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allClubs: LiveData<List<ClubsModel>>

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    fun getClub(face: String?): LiveData<ClubsModel> = mClubDao.fetchAllData(face)


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    fun insert(club: ClubsModel?) {
        ClubRoomDatabase.databaseWriteExecutor.execute { mClubDao.insert(club) }
    }

    // Note that in order to unit test the ClubRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    init {
        val db = ClubRoomDatabase.getDatabase(application!!)
        mClubDao = db!!.clubDao()!!
        allClubs = mClubDao.allClubsASC
    }
}