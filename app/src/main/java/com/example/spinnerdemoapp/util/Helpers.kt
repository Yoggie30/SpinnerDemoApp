package com.example.statelistdemo.util

import android.content.Context
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun getFileContentsAsString(context: Context, file: String?): String? {
    var str: String? = ""
    try {
        val assetManager = context.assets
        var `in`: InputStream? = null
        try {
            `in` = assetManager.open(file!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val isr = InputStreamReader(`in`)
        val inputBuffer = CharArray(100)
        var charRead: Int
        while (isr.read(inputBuffer).also { charRead = it } > 0) {
            val readString = String(inputBuffer, 0, charRead)
            str += readString
        }
    } catch (ioe: IOException) {
        ioe.printStackTrace()
    }
    return str
}

