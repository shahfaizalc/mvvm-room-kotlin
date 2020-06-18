package com.faizal.android.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faizal.android.database.R
import com.faizal.android.database.databinding.ActivityHomeBinding
import com.faizal.android.injection.App
import com.faizal.android.injection.module.ConstantsModule
import com.faizal.android.listeners.MultipleClickListener
import com.faizal.android.model.ClubsModel
import com.faizal.android.utils.NetworkStateHandler
import com.faizal.android.utils.SortType
import com.faizal.android.utils.isMultipleCall
import com.faizal.android.viewmodel.ClubsViewModel
import kotlinx.android.synthetic.main.customappbar.view.*
import javax.inject.Inject

class ClubsActivity : AppCompatActivity(), MultipleClickListener, NetworkStateHandler.NetworkStateListener {

    val TAG = "ClubsActivity";

    @Inject
    lateinit var networkStateHandler: NetworkStateHandler

    @Inject
    lateinit var constantsModule: ConstantsModule

    var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(binding!!.included.toolbar);
        val drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_variant);
        binding!!.included.toolbar.setOverflowIcon(drawable);

        // Get a new or existing ViewModel from the ViewModelProvider.
        val clubsViewModel = ViewModelProvider(this).get(ClubsViewModel::class.java)
        binding!!.setMainData(clubsViewModel)

        clubsViewModel.allClubs.observe(this, Observer { clubs: List<ClubsModel> ->
            // Update the cached copy of the clubs in the adapter.
            clubsViewModel.adapter!!.setClubs(clubs)
        })

        clubsViewModel.clickedItem.observe(this, Observer { club: String? -> launchActivity(club) })
        clubsViewModel.progressVisible.observe(this, Observer { club: Int? -> progressVisibility(club) })
        clubsViewModel.getMsgView().observe(this, Observer { club: Int? -> msgVisibilty(club) })
    }


    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        unRegisterListeners()
    }

    fun registerListeners() {
        networkStateHandler.registerNetWorkStateBroadCast(this)
        networkStateHandler.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler.unRegisterNetWorkStateBroadCast(this)
    }

    override fun onNetworkStateReceived(online: Boolean) {
        Log.d(TAG, "onNetWorkStateReceivedM:$online")
        when (online) {
            true -> msgVisibilty(View.GONE)
            false -> msgVisibilty(View.VISIBLE)
        }
    }


    private fun progressVisibility(clubName: Int?) {
        binding!!.progress = clubName!!
    }

    private fun msgVisibilty(clubName: Int?) {
        binding!!.msg = clubName!!
    }


    private fun launchActivity(clubName: String?) {
        if (handleMultipleClicks()) {
            val intent = (Intent(this, DetailedActivity::class.java))
            intent.putExtra(constantsModule.bundleKeyClubName(), clubName)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                Log.d(TAG, "List sorted by name")
                binding!!.mainData!!.adapter!!.reOrder(SortType.NAME)
                true
            }
            R.id.item2 -> {
                Log.d(TAG, "List sorted by club value")
                binding!!.mainData!!.adapter!!.reOrder(SortType.VALUE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun handleMultipleClicks(): Boolean {
        return isMultipleCall()
    }

}