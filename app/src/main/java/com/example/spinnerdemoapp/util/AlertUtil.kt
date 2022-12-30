package com.example.spinnerdemoapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spinnerdemoapp.R
import com.example.spinnerdemoapp.data.model.AlertItem
import com.example.spinnerdemoapp.databinding.AlertLayoutBinding

import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AlertUtil {
    @SuppressLint("NotifyDataSetChanged")
    fun showAlertList(
        context: Context,
        title: String,
        listItems: List<AlertItem>,
        listAlertItemSelectedListener: (Int) -> Unit
    ): AlertDialog? {
        val dialogBuilder =
            MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
        var dialogCreated: AlertDialog? = null
        var selectedPosition: Int = 0
        val inflater = LayoutInflater.from(context)
        val bind: AlertLayoutBinding = AlertLayoutBinding.inflate(inflater)
        dialogBuilder.setView(bind.root)
        bind.titleTextView.text = title
        var customAlertListAdapter: CustomAlertListAdapter? = null
        bind.alertItems.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        customAlertListAdapter = CustomAlertListAdapter(listItems) { selectedItem, position ->
            listItems.forEach {
                it.selected = false
                selectedItem.selected = true
                customAlertListAdapter?.notifyDataSetChanged()
            }
            selectedPosition = position
        }
        bind.closeImageView.setOnClickListener {
            dialogCreated?.dismiss()
        }
        bind.dialogDoneButton.setOnClickListener {
            listAlertItemSelectedListener(selectedPosition)
            dialogCreated?.dismiss()
        }
        bind.alertItems.adapter = customAlertListAdapter
        dialogCreated = dialogBuilder.create()
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val displayHeight = displayMetrics.heightPixels
        val dialogWindowWidth = (displayWidth * 0.9f).toInt()
        val dialogWindowHeight = (displayHeight * 0.8f).toInt()
        dialogCreated.window?.setLayout(dialogWindowWidth, dialogWindowHeight)
        dialogCreated.show()
        return dialogCreated
    }
}