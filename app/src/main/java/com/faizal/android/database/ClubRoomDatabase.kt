package com.faizal.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.faizal.android.model.ClubsModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

 /**
 * This is the backend. The database. This used to be done by the OpenHelper.
 */
@Database(entities = [ClubsModel::class], version = 1, exportSchema = false)
internal abstract class ClubRoomDatabase : RoomDatabase() {
    abstract fun clubDao(): ClubDao?

    companion object {
        val dbName = "club_database"

        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: ClubRoomDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        fun getDatabase(context: Context): ClubRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(ClubRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                ClubRoomDatabase::class.java, dbName)
                                .addCallback(sRoomDatabaseCallback)
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        /**
         *
         * If we want to populate the database only when the database is created for the 1st time,
         * override RoomDatabase.Callback()#onCreate
         */
        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                // If you want to keep data through app restarts,
                // comment out the following block
                databaseWriteExecutor.execute {}
            }
        }
    }
}