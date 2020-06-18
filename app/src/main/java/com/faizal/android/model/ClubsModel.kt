package com.faizal.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class ClubsModel(
        @PrimaryKey
        val name: String,
        val country: String,
        val image: String,
        val value: Long,
        val european_titles: Long)



