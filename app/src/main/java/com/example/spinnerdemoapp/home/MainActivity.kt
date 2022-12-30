package com.example.spinnerdemoapp.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.spinnerdemoapp.R
import com.example.spinnerdemoapp.data.model.AlertItem
import com.example.spinnerdemoapp.databinding.ActivityMainBinding
import com.example.spinnerdemoapp.util.AlertUtil
import com.example.statelistdemo.util.getFileContentsAsString
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var stateList: MutableList<AlertItem> = arrayListOf()
    private var cityList: List<AlertItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchStateList()
        init()
    }

    private fun init() {
        binding.apply {
            stateAutoCompleteView.setOnClickListener {
                showStateList()
            }
            cityAutoCompleteView.setOnClickListener {
                showCityList()
            }
        }
    }

    private fun fetchStateList() {
        try {
            val jsonStr: String = getFileContentsAsString(this, "States.json") ?: ""
            val itemArray = JSONArray(jsonStr)
            for (i in 0 until itemArray.length()) {
                val stateJson = JSONObject(itemArray.getString(i))
                val listOfCities = mutableListOf<AlertItem>()
                val cities = stateJson.getJSONArray("citiesList")

                for (j in 0 until cities.length()) {
                    val cityJson = JSONObject(cities.getString(j))
                    listOfCities.add(
                        AlertItem(
                            itemCode = cityJson.getString("code"),
                            itemTitle = cityJson.getString("description"),
                        )
                    )
                }

                stateList.add(
                    AlertItem(
                        itemCode = stateJson.getString("code"),
                        itemTitle = stateJson.getString("description"),
                        listData = listOfCities
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e(TAG, "Error while trying to add post to database")
        } finally {
            Log.e(TAG, "stateList size: ${stateList.size}")
            Log.e(TAG, "Data Fetched")
        }
    }

    private fun showStateList() {
        binding.apply {
            stateList.takeIf { it.isNotEmpty() }?.let { states ->
                AlertUtil.showAlertList(
                    binding.root.context,
                    getString(R.string.label_select_state),
                    states
                ) { position ->
                    stateAutoCompleteView.setText(stateList[position].itemTitle ?: "")
                    stateAutoCompleteView.tag = stateList[position].itemCode
                    cityAutoCompleteView.setText("")
                    cityAutoCompleteView.tag = ""
                    cityList = stateList[position].listData
                }
            }
        }
    }

    private fun showCityList() {
        binding.apply {
            if (stateAutoCompleteView.tag == "") {
                showAlert("Please Select State First")
            } else {

                cityList.takeIf { it.isNotEmpty() }?.let { cities ->
                    AlertUtil.showAlertList(
                        binding.root.context,
                        getString(R.string.label_select_city),
                        cities
                    ) { position ->
                        cityAutoCompleteView.setText(cityList[position].itemTitle ?: "")
                    }
                }
            }
        }
    }

    private fun showAlert(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.app_name)
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.label_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    companion object {
        const val TAG = "MainActivity"
    }

}