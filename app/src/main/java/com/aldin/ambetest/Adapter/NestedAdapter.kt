package com.aldin.ambetest.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aldin.ambetest.R
import com.aldin.ambetest.database.DrugEntity

class NestedAdapter(private val drugs: List<DrugEntity>) :
    RecyclerView.Adapter<NestedAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvDrugName)
        val descTextView: TextView = itemView.findViewById(R.id.tvDrugDesc)
//        val doseTextView: TextView = itemView.findViewById(R.id.tvDrugDose)
//        val frequencyTextView: TextView = itemView.findViewById(R.id.tvDrugFrequency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_drug, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDrug = drugs[position]
        holder.nameTextView.text = currentDrug.drug_name
        holder.descTextView.text = "minum ${currentDrug.dose} ${currentDrug.dose_type} ${currentDrug.drug_type}"

//        holder.doseTextView.text = "Dose: ${currentDrug.dose}"
//        holder.frequencyTextView.text = "Frequency: ${currentDrug.frequency}x per day"
    }

    override fun getItemCount(): Int {
        return drugs.size
    }
}