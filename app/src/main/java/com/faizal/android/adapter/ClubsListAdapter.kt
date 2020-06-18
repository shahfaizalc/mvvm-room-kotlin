package com.faizal.android.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.accenture.list.listeners.AlbumsEventListener
import com.faizal.android.adapter.ClubsListAdapter.ClubViewHolder
import com.faizal.android.database.R
import com.faizal.android.database.databinding.RecyclerviewItemBinding
import com.faizal.android.model.ClubsModel
import com.faizal.android.utils.SortByClubValue
import com.faizal.android.utils.SortByTitle
import com.faizal.android.utils.SortType
import com.faizal.android.viewmodel.ClubsViewModel
import java.util.*

class ClubsListAdapter(context: Context?, private var channelsViewModel: ClubsViewModel)
    : RecyclerView.Adapter<ClubViewHolder>(), AlbumsEventListener {

    val TAG = "ClubsListAdapter";

    inner class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recyclerviewItemBinding: RecyclerviewItemBinding? = DataBindingUtil.bind(itemView)
    }

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClubs // Cached copy of clubs
            : List<ClubsModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return ClubViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        if (mClubs != null) {
            holder.recyclerviewItemBinding!!.albumsInfoModel =  mClubs!![position]
            holder.recyclerviewItemBinding!!.albumsInfoViewModel = channelsViewModel
            holder.recyclerviewItemBinding!!.itemClickListener = this
            holder.recyclerviewItemBinding!!.itemPosition = position
            holder.recyclerviewItemBinding!!.executePendingBindings()
        }
    }

    fun setClubs(clubs: List<ClubsModel>) {
        mClubs = clubs
        notifyDataSetChanged()
    }

    fun reOrder(orderType: SortType){
        Log.d(TAG,"list sorting initiated ");
        when(orderType){
           SortType.NAME -> Collections.sort(mClubs, SortByTitle())
           SortType.VALUE -> Collections.sort(mClubs, SortByClubValue())
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mClubs != null) mClubs!!.size else 0
    }

    override fun onClickAlbumsListItem(albumsViewModel: ClubsViewModel, position: Int) {

        Log.d(TAG,"clicked on "+ albumsViewModel.allClubs.value!![position].name);

        albumsViewModel.launchDetailed(albumsViewModel.allClubs.value!![position].name);

    }
}