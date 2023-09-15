package com.aldin.ambetest.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.aldin.ambetest.R
import com.aldin.ambetest.database.DrugEntity

class DrugListAdapter(context: Context, private val drugs: ArrayList<DrugEntity>) : ArrayAdapter<DrugEntity>(context, 0, drugs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_drug, parent, false)
        }

        // Get the drug at the current position
        val currentDrug = getItem(position)

        // Set the name, dose, and frequency of the drug
        val nameTextView = listItemView?.findViewById<TextView>(R.id.tvDrugName)
        nameTextView?.text = "Nama Obat =  ${currentDrug?.drug_name}"

        val doseTextView = listItemView?.findViewById<TextView>(R.id.tvDrugDose)
        doseTextView?.text = "Dosis = ${currentDrug?.dose} ${currentDrug?.dose_type}"

        val frequencyTextView = listItemView?.findViewById<TextView>(R.id.tvDrugFrequency)
        frequencyTextView?.text = "Frekuensi = ${currentDrug?.frequency}x Sehari"

        return listItemView!!
    }
}