package com.faizal.android.utils

import com.faizal.android.model.ClubsModel

import java.util.Comparator

/**
 * The SortByTitle class implements an application that
 * simply sorts given list.
 */
class SortByTitle : Comparator<ClubsModel> {

    override fun compare(list1: ClubsModel, list2: ClubsModel): Int {
        return list2.name.let { list1.name.compareTo(it) }
    }
}

/**
 * The Sort by Club value class implements an application that
 * simply sorts given list.
 */
class SortByClubValue : Comparator<ClubsModel> {

    override fun compare(list1: ClubsModel, list2: ClubsModel): Int {
        return list2.value.let { list1.value.compareTo(it) }
    }
}