package com.faizal.android.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.util.Log

/**
 *
 * Class to handle network state
 */
class NetworkStateHandler {

    private val TAG = "NetworkStateHandler"
    private var networkStateListener: NetworkStateListener? = null
    private var state: Boolean = false

    private val networkStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onReceiveHandler(intent)
        }
    }

    private fun onReceiveHandler(intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.action!!, ignoreCase = true)) {
            Handler().post {
                if (networkStateListener != null) {
                    state = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                            java.lang.Boolean.FALSE)
                    networkStateListener!!.onNetworkStateReceived(state)
                }
            }
        }
    }

    fun registerNetWorkStateBroadCast(context: Context) {
        Log.d(TAG, "registerNetWorkStateBroadCast")
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkStateReceiver, intentFilter)
    }

    fun unRegisterNetWorkStateBroadCast(context: Context) {
        Log.d(TAG, "unRegisterNetWorkStateBroadCast")
        try {
            context.unregisterReceiver(networkStateReceiver)
        } catch (ex: Exception) {
            Log.d(TAG, "unRegisterNetWorkStateBroadCast" + ex.localizedMessage)
        }
    }

    fun setNetworkStateListener(networkStateListener: NetworkStateListener) {
        this.networkStateListener = networkStateListener
    }

    interface NetworkStateListener {
        fun onNetworkStateReceived(online: Boolean)
    }

}
