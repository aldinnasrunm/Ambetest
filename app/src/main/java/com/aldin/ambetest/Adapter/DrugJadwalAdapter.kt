package com.aldin.ambetest.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.aldin.ambetest.R
import com.aldin.ambetest.database.DrugEntity

class DrugJadwalAdapter(context: Context, private val drugs: ArrayList<DrugEntity>) :
    ArrayAdapter<DrugEntity>(context, 0, drugs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
//            listItemView = LayoutInflater.from(context).inflate(R.layout.layout_item_drug, parent, false)
            listItemView = LayoutInflater.from(context).inflate(R.layout.layout_nested_item_drug, parent, false)

        }

        val currentData = getItem(position)


//        set the jadwal Name
        val jadwalJam = listItemView?.findViewById<TextView>(R.id.tv_hours)
//        addSwitchCase
        when (position) {
            0 -> jadwalJam?.text = "08.00 WIB"
            1 -> jadwalJam?.text = "12.00 WIB"
            2 -> jadwalJam?.text = "16.00 WIB"
        }

        val listObat = listItemView?.findViewById<ListView>(R.id.lv_drugs_hours)
//        listObat?.adapter = DrugAdapter(context, drugs[position])



        // Get the drug at the current position
//        val currentDrug = getItem(position)

        // Set the name, dose, and frequency of the drug
//        val nameTextView = listItemView?.findViewById<TextView>(R.id.tvDrugName)
//        nameTextView?.text = "Nama Obat =  ${currentDrug?.drug_name}


        // Set the name, dose, and frequency of the drug
//        val nameTextView = listItemView?.findViewById<TextView>(R.id.tvDrugName)
//        nameTextView?.text = "Nama Obat =  ${currentDrug?.drug_name}"

//        val descTextView = listItemView?.findViewById<TextView>(R.id.tvDrugDesc)
//        descTextView?.text = "Dosis = ${currentDrug?.dose} ${currentDrug?.dose_type}"

//        val frequencyTextView = listItemView?.findViewById<TextView>(R.id.tvDrugFrequency)
//        frequencyTextView?.text = "Frekuensi = ${currentDrug?.frequency}x Sehari"

        return listItemView!!
    }
}
