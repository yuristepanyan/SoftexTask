package com.softex.task.util

import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun inflateView(parent: ViewGroup, @LayoutRes res: Int): View {
    return LayoutInflater.from(parent.context).inflate(res, parent, false)
}