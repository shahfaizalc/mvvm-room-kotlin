package com.faizal.android.handlers

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faizal.android.communication.RestHandler
import com.faizal.android.utils.EndlessRecyclerViewScrollListener
import com.faizal.android.utils.SortByTitle
import com.faizal.android.model.ClubsModel
import com.faizal.android.adapter.ClubsListAdapter
import com.faizal.android.injection.App
import com.faizal.android.injection.module.ConstantsModule
import com.faizal.android.viewmodel.ClubsViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject


/**
 *  Class to handle recyclerview scroll listener and to initiate server call
 */
class RecyclerLoadClubsHandler(private val albumsViewModel: ClubsViewModel,
                               private val listViewAdapter: ClubsListAdapter) {

    private val TAG = "RecyclerHandler"

    @Inject
    lateinit var constantsModule : ConstantsModule

    @Inject
    lateinit var retrofitClientInstance : RestHandler

    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        //To use in case we have pagination in response.
        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            }
        }
        recyclerView.addOnScrollListener(listener)
    }

    fun initRequest(view: RecyclerView, urlSubString: String) {
        Log.d(TAG, "initialise server request : sub url $urlSubString")
        App.component.inject(this)
        albumsViewModel.setProgressBarVisible(View.VISIBLE)
        albumsViewModel.setMsgView(View.GONE)
        launchRequest(urlSubString, view)
    }

    private fun launchRequest(urlSubString: String, view: RecyclerView) {

        runBlocking {
            val handler = coroutineExceptionHandler()
            GlobalScope.launch(handler) {

                val service = retrofitClientInstance.getServiceClubs(constantsModule.baseUrl(), view.context)
                val repositories = withContext(Dispatchers.Default) {
                    service.retrieveRepositoriesAsync(urlSubString).await()
                }
                withContext(Dispatchers.Default) { coroutineSuccessHandler(repositories) }
                albumsViewModel.setProgressBarVisible(View.GONE)
            }
        }
    }

    private fun coroutineExceptionHandler() = CoroutineExceptionHandler { _, exception ->

        Log.d(TAG, "coroutineHandler:exception $exception.message")

        albumsViewModel.setProgressBarVisible(View.GONE)
        if (null != exception.message) {
            albumsViewModel.setMsgView(View.VISIBLE)
        }
    }

    private fun coroutineSuccessHandler(response: List<ClubsModel>) {
        Log.d(TAG, "coroutineHandler:success$response")

        //Default the app will be sorted in Ascending
        Collections.sort(response, SortByTitle())

        //Insert elements
        val elements = response as Iterable<ClubsModel>
        albumsViewModel.insertAll(elements)
    }


}
