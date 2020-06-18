package com.faizal.android.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faizal.android.model.ClubsModel

@Dao
interface ClubDao {

    @get:Query("SELECT * from team_table ORDER BY name ASC")
    val allClubsASC: LiveData<List<ClubsModel>>

    @Query("SELECT * FROM team_table WHERE name= :myName")
    fun fetchAllData(myName: String?): LiveData<ClubsModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(club: ClubsModel?)

    @Query("DELETE FROM team_table")
    fun deleteAll()
}