package com.softex.task.arch.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.softex.task.R
import com.softex.task.arch.main.adapter.ItemsAdapter
import com.softex.task.arch.main.adapter.SwipeToDeleteCallback
import com.softex.task.extensions.showAlert
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    /**
     * The method that initializes the recycler view and the activity items' bindings
     */
    private fun init() {
        recycler.adapter = adapter
        initSwipeToDelete()

        bind()
    }

    /**
     * Binds the logic and the activity together
     */
    private fun bind() {
        RxSwipeRefreshLayout.refreshes(swipeRefresh).map { false }.subscribe(viewModel.getData)
        viewModel.getData.onNext(true)

        viewModel.itemsList.observe(this, Observer { adapter.submitList(it) })
        viewModel.error.observe(this, Observer { showAlert(it ?: "") })
        viewModel.dataReceived.observe(this, Observer { swipeRefresh.isRefreshing = false })
        viewModel.showLoading.observe(
            this,
            Observer { loading.visibility = if (it == true) VISIBLE else GONE }
        )
    }

    /**
     * Initializes the swipe motion for deletion and attaches it to the recycler view
     */
    private fun initSwipeToDelete() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.getItemPublic(viewHolder.adapterPosition)?.apply {
                    deleted = true
                    viewModel.removeItem.onNext(this)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recycler)
    }
}
