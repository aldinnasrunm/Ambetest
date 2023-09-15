package com.aldin.ambetest.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldin.ambetest.R
import com.aldin.ambetest.database.DrugEntity

class OuterAdapter(private val drugLists: List<List<DrugEntity>>) :
    RecyclerView.Adapter<OuterAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hours: TextView = itemView.findViewById(R.id.tv_hours)
        val nestedRecyclerView: RecyclerView = itemView.findViewById(R.id.lv_drugs_hours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_nested_item_drug, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drugList = drugLists[position]
        val nestedAdapter = NestedAdapter(drugList)
        when (position) {
            0 -> holder.hours?.text = "08.00 WIB"
            1 -> holder.hours?.text = "12.00 WIB"
            2 -> holder.hours?.text = "16.00 WIB"
        }
        holder.nestedRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.nestedRecyclerView.adapter = nestedAdapter
    }

    override fun getItemCount(): Int {
        return drugLists.size
    }
}