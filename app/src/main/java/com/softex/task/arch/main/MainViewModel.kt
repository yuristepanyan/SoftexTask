package com.softex.task.arch.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.google.gson.Gson
import com.softex.task.db.ItemDao
import com.softex.task.extensions.configThread
import com.softex.task.extensions.handleError
import com.softex.task.extensions.toResponse
import com.softex.task.model.Item
import com.softex.task.net.ItemsApi
import com.softex.task.util.ResponseErrorHandler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

private const val DB_TAG = "db"

class MainViewModel(
    private val api: ItemsApi,
    private val errorHandler: ResponseErrorHandler,
    private val gson: Gson,
    private val itemDao: ItemDao,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val saveData = PublishSubject.create<List<Item>>()

    /** inputs */
    val getData = PublishSubject.create<Boolean>()
    val removeItem = PublishSubject.create<Item>()

    /** outputs */
    val error = MutableLiveData<String>()
    val dataReceived = MutableLiveData<Unit>()
    val showLoading = MutableLiveData<Boolean>()
    var itemsList: LiveData<PagedList<Item>> = initItemList()

    init {
        getData()
        saveData()
        removeItem()
    }

    /**
     * Connects to the API and gets items
     */
    private fun getData() {
        val request = getData.doOnNext {
            if (it) {
                showLoading.postValue(true)
            }
        }
            .flatMapSingle { api.getItems().toResponse(errorHandler) }
            .share()

        request.filter { it.isSuccessful }
            .subscribe {
                dataReceived.postValue(Unit)

                val data = it.body()
                data?.apply {
                    saveData.onNext(this)
                }
                hideLoading()
            }
            .addTo(disposable)

        request.filter { !it.isSuccessful }
            .subscribe { hideLoading() }
            .addTo(disposable)

        request.handleError(error, gson).addTo(disposable)
    }

    /**
     * Saves the received data to the database
     */
    private fun saveData() {
        saveData.flatMapCompletable { itemDao.insertItems(it).configThread() }
            .subscribe({}, { error ->
                Log.e(DB_TAG, "Unable to add items", error)
                print(error.localizedMessage)
            })
            .addTo(disposable)
    }

    /**
     * Removes an item from the database by setting the deleted field to TRUE
     */
    private fun removeItem() {
        removeItem.flatMapCompletable { itemDao.updateItem(it).configThread() }
            .subscribe()
            .addTo(disposable)
    }

    private fun hideLoading() {
        showLoading.postValue(false)
    }

    private fun initItemList() = itemDao.getItems().toLiveData(pageSize = 20)

    /**
     * Is triggered when the activity is closed for some time, disposes the disposable
     */
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}