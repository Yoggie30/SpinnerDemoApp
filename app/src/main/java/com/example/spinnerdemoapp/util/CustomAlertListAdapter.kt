package com.example.spinnerdemoapp.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.spinnerdemoapp.R
import com.example.spinnerdemoapp.data.model.AlertItem
import com.example.spinnerdemoapp.databinding.LayoutAlertItemBinding


class CustomAlertListAdapter(
    var alertItems: List<AlertItem>,
    var callback: (AlertItem, Int) -> Unit
) : RecyclerView.Adapter<CustomAlertListAdapter.CustomAlertViewHolder>() {

    override fun getItemCount(): Int {
        return alertItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAlertViewHolder {
        val binding =
            LayoutAlertItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomAlertViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: CustomAlertViewHolder, position: Int) {
        val type = alertItems[position]
        holder.bind(type)
        holder.itemView.setOnClickListener {
            callback(alertItems[position], position)
        }
    }

    inner class CustomAlertViewHolder(
        private val binding: LayoutAlertItemBinding,
        context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlertItem) {
            with(binding)
            {
                itemTextView.text = item.itemTitle
                alertImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_radio_off))

                if (item.selected) {
                    alertImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_radio_on))
                }
            }
        }
    }
}

