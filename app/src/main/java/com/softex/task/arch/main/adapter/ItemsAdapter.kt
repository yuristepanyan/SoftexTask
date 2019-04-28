package com.softex.task.arch.main.adapter

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.softex.task.R
import com.softex.task.helper.DateTimeFormat
import com.softex.task.model.Item
import com.softex.task.util.inflateView
import kotlinx.android.synthetic.main.recycler_item.view.*
import javax.inject.Inject

class ItemsAdapter @Inject constructor(val context: Context, val dateTimeFormat: DateTimeFormat) :
    PagedListAdapter<Item, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        type: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ViewHolder(inflateView(parent, R.layout.recycler_item))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Used for giving access to the item from the outside
     */
    fun getItemPublic(position: Int): Item? {
        return super.getItem(position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        /**
         * Setup view data.
         * @param item for getting data
         */
        fun bind(item: Item?) {
            item?.let {
                view.title.text = it.name
                view.time.text = dateTimeFormat.getAppDate(it.time)
                item.image?.let { url -> loadImage(url) } ?: hideImage()
            }
        }

        /**
         * Hiding an image. called when an image doesn't exist
         */
        private fun hideImage() {
            view.image.visibility = GONE
        }

        /**
         * Image loading logic
         */
        private fun loadImage(url: String) {
            view.image.visibility = VISIBLE
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(CenterCrop()))
                .into(view.image)
        }
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }

}