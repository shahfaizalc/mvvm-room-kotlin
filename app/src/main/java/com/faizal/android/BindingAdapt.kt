package com.faizal.android

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faizal.android.handlers.RecyclerLoadClubsHandler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.faizal.android.adapter.ClubsListAdapter
import com.faizal.android.database.R
import com.faizal.android.injection.module.ConstantsModule
import com.faizal.android.utils.imageLoad
import com.faizal.android.viewmodel.ClubsViewModel

@BindingAdapter("app:clubsRecycler")
fun adapter(recyclerView: RecyclerView, channelsViewModel: ClubsViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = ClubsListAdapter(recyclerView.context, channelsViewModel)
    channelsViewModel.setListAdapter(listAdapter)
    val bindingAdapter = RecyclerLoadClubsHandler(channelsViewModel, listAdapter)

    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
    bindingAdapter.initRequest(recyclerView, ConstantsModule().subUrl_param())
}

/**
 * To display images
 * @param view     image view
 * @param imageUrl image URL
 */
@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView?, imageUrl: String?) {
    imageLoad(view, imageUrl)
}


