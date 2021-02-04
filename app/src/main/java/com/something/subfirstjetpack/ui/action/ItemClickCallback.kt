package com.something.subfirstjetpack.ui.action

import android.view.View

class ItemClickCallback(private val onItemClickCallback: OnItemClickCallback): View.OnClickListener {
    interface OnItemClickCallback {
        fun onItemClicked(v: View)
    }

    override fun onClick(v: View) {
        onItemClickCallback.onItemClicked(v)
    }

}