package com.faizal.android.database;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.faizal.android.model.ClubsModel;

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4.class)
public class ClubDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ClubDao mClubDao;
    private ClubRoomDatabase mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(context, ClubRoomDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mClubDao = mDb.clubDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertAndGetClub() throws Exception {
        ClubsModel club = new ClubsModel("club","club","club",12,23);
        mClubDao.insert(club);
        List<ClubsModel> models = LiveDataTestUtil.getValue(mClubDao.getAllClubsASC());
        assertEquals(models.get(0).getName(), club.getName());
    }

    @Test
    public void getAllClubs() throws Exception {
        ClubsModel club = new ClubsModel("club","club","club",12,23);
        mClubDao.insert(club);
        ClubsModel clubs2 = new ClubsModel("club","club","club",12,23);
        mClubDao.insert(clubs2);
        List<ClubsModel> allClubs = LiveDataTestUtil.getValue(mClubDao.getAllClubsASC());
        assertEquals(allClubs.get(0).getName(), club.getName());
        assertEquals(allClubs.get(1).getName(), clubs2.getName());
    }

    @Test
    public void deleteAll() throws Exception {
        ClubsModel club = new ClubsModel("club","club","club",12,23);
        mClubDao.insert(club);
        ClubsModel clubs2 = new ClubsModel("club","club","club",12,23);
        mClubDao.insert(clubs2);
        mClubDao.deleteAll();
        List<ClubsModel> allClubs = LiveDataTestUtil.getValue(mClubDao.getAllClubsASC());
        assertTrue(allClubs.isEmpty());
    }
}
