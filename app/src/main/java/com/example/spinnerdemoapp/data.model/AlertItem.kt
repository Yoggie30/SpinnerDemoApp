package com.example.spinnerdemoapp.data.model

data class AlertItem(
    var itemCode: String? = null,
    var itemTitle: String? = null,
    var listData: MutableList<AlertItem> = arrayListOf(),
    var selected: Boolean = false
)
