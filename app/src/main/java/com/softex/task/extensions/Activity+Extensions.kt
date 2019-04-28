package com.softex.task.extensions

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.softex.task.R

fun AppCompatActivity.showAlert(msg: String) {
    (AlertDialog.Builder(this))
            .setTitle(null)
            .setPositiveButton(R.string.close, null)
            .setMessage(msg)
            .show()
}