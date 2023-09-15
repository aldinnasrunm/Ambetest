package com.aldin.ambetest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldin.ambetest.Adapter.DrugJadwalAdapter
import com.aldin.ambetest.Adapter.NestedAdapter
import com.aldin.ambetest.Adapter.OuterAdapter
import com.aldin.ambetest.R
import com.aldin.ambetest.database.DrugEntity
import com.aldin.ambetest.database.PersonData
import com.aldin.ambetest.databinding.ActivityDashboardBinding
import com.aldin.ambetest.databinding.DrugDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {

    private lateinit var database: PersonData
    private lateinit var bd : ActivityDashboardBinding
    private lateinit var  adapterJadwal : DrugJadwalAdapter
    private lateinit var outerAdapter: OuterAdapter
    private lateinit var nestedAdapter: NestedAdapter
    private lateinit var  arrayDrug : ArrayList<DrugEntity>
    private lateinit var drugDose1: List<DrugEntity>
    private lateinit var drugDose2: List<DrugEntity>
    private lateinit var drugDose3: List<DrugEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bd.root)

        database = PersonData.getInstance(this)
        arrayDrug = ArrayList<DrugEntity>()
        drugDose1 = ArrayList<DrugEntity>()
        drugDose2 = ArrayList<DrugEntity>()
        drugDose3 = ArrayList<DrugEntity>()
        showData()

        bd.fabAddMed.setImageResource(R.drawable.ic_add_med)
        bd.fabAddMed.setOnClickListener {
            addObat()
        }

    }

    private fun addObat() {
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.drug_dialog, null)
        val pbd = DrugDialogBinding.bind(dialogView)
        val options = arrayOf("Tablet", "Pil", "Kapsul")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pbd.spDosisObat.adapter = adapter
        pbd.spDosisObat.setSelection(0)

//        val adapterLV = DrugListAdapter(this, listDrug)
//        bd.lvObat.adapter = adapterLV

        // Update the ListView when the listDrug is updated
//        adapterLV.notifyDataSetChanged()


        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Tambah") { dialog, which ->

                if (pbd.rgFrekObat.checkedRadioButtonId != -1 &&
                    pbd.rgJenisObat.checkedRadioButtonId != -1 &&
                    pbd.etNamaObat.text.toString().isNotEmpty() &&
                    pbd.etDosisObat.text.toString().isNotEmpty() &&
                    pbd.etJumlahObat.text.toString().isNotEmpty()
                ) {

                    val namaObat = pbd.etNamaObat.text.toString()
                    val rgJenisObat = pbd.rgJenisObat.checkedRadioButtonId + 1
                    lateinit var jenisObat: String
                    var frekObat: Int = 0
                    if (pbd.rbSebelum.isChecked) {
                        jenisObat = "Sebelum Makan"
                    } else {
                        jenisObat = "Sesudah Makan"
                    }
                    //                get data from edit text and radio button

                    if (pbd.rb1x.isChecked) {
                        frekObat = 1
                    } else if (pbd.rb2x.isChecked) {
                        frekObat = 2
                    } else if (pbd.rb3x.isChecked) {
                        frekObat = 3
                    }

//                    val frekObat = pbd.rgFrekObat.checkedRadioButtonId +1
                    val dosisObat = pbd.etDosisObat.text.toString().toInt()
                    val jenisDosis = pbd.spDosisObat.selectedItem.toString()
                    val jumlahObat = pbd.etJumlahObat.text.toString().toInt()

                    val drug = DrugEntity(
                        drug_name = namaObat,
                        frequency = frekObat,
                        drug_type = jenisObat,
                        dose = dosisObat,
                        dose_type = jenisDosis,
                        amount = jumlahObat
                    )

//                    listDrug.add(drug)
                    // add data to database
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            database.drugDao().insert(drug)
                        }
                        updateView()
                    }

//                    bd.lvObat.visibility = View.VISIBLE
//                    adapterLV.notifyDataSetChanged()

                    Toast.makeText(this, "Success add drug!", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this, "Please fill all field!", Toast.LENGTH_SHORT).show()
                }


            }
            .setNegativeButton("Batal", null)

//        display the AlertDialog
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()

    }


    fun showData(){
        lifecycleScope.launch {

//            validate person data
            val personData = withContext(Dispatchers.IO) {
                database.personDao().getAllMyEntities()
            }
            var cr = ""
            for (person in personData){
                cr += "\n ${person.id} | ${person.name}"
                Log.d("TAG", "Database Value Person: ${person.id}, ${person.name}, ${person.age}")
            }

//            validate jadwal data into list
            val drugs : List<DrugEntity> = withContext(Dispatchers.IO) {
                database.drugDao().getAllMyEntities()
            }

            for(drugItem in drugs){
                Log.d("TAG", "Database Value Drug: ${drugItem.id}, ${drugItem.drug_name}, ${drugItem.dose}")
                arrayDrug.add(drugItem)

//                if the drug frequency is 1x add to drugDose1, if 2x add to drugDose1 drugDose2, if 3x add to drugDose1 drugDose2 drugDose3
                when(drugItem.frequency){
                    1 -> {
                        (drugDose1 as ArrayList<DrugEntity>).add(drugItem)
                    }
                    2 -> {
                        (drugDose1 as ArrayList<DrugEntity>).add(drugItem)
                        (drugDose2 as ArrayList<DrugEntity>).add(drugItem)
                    }
                    3 -> {
                        (drugDose1 as ArrayList<DrugEntity>).add(drugItem)
                        (drugDose2 as ArrayList<DrugEntity>).add(drugItem)
                        (drugDose3 as ArrayList<DrugEntity>).add(drugItem)
                    }
                }
            }


//            make list of drugDose1 drugDose2 drugDose3
            val arrayDrugDose = ArrayList<List<DrugEntity>>()
            arrayDrugDose.add(drugDose1)
            arrayDrugDose.add(drugDose2)
            arrayDrugDose.add(drugDose3)

//            insert to logD arrayDrugDose
            for (logD in arrayDrugDose){
                Log.d("TAG", "Database Value DrugDose: ${logD}")
            }

            val outerRecyclerView: RecyclerView = findViewById(R.id.lvMedMorning)
            val dataForOuterAdapter: List<List<DrugEntity>> = arrayDrugDose // Your data here

            val outerAdapter = OuterAdapter(dataForOuterAdapter)
            outerRecyclerView.layoutManager = LinearLayoutManager(this@DashboardActivity)
            outerRecyclerView.adapter = outerAdapter

//            adapterJadwal = DrugJadwalAdapter(this@DashboardActivity, arrayDrugDose)

//            bd.lvMedMorning.adapter = adapterJadwal
//            adapterJadwal.notifyDataSetChanged()

//            bd.lvMedMorning.adapter = outerAdapter


        }
    }

    suspend fun deleteByID(id: Int){
        withContext(Dispatchers.IO){
            database.personDao().deleteById(id)
            updateView()
        }
    }


    fun updateView(){
        showData()
    }
}