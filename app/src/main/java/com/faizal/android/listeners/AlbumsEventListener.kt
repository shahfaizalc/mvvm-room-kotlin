package com.accenture.list.listeners

import com.faizal.android.viewmodel.ClubsViewModel

interface AlbumsEventListener {

    fun onClickAlbumsListItem(albumsViewModel : ClubsViewModel, position: Int)

}