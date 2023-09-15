package com.aldin.ambetest.View

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.aldin.ambetest.Adapter.DrugListAdapter
import com.aldin.ambetest.MainActivity
import com.aldin.ambetest.R
import com.aldin.ambetest.database.DrugEntity
import com.aldin.ambetest.database.PersonData
import com.aldin.ambetest.database.PersonDataCarry
import com.aldin.ambetest.database.PersonEntity
import com.aldin.ambetest.databinding.ActivityAngketObatBinding
import com.aldin.ambetest.databinding.DrugDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class AngketObatActivity : AppCompatActivity() {

    private lateinit var bd: ActivityAngketObatBinding
    private lateinit var listDrug: ArrayList<DrugEntity>
    private lateinit var personCarry: PersonDataCarry
    private lateinit var datePick: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityAngketObatBinding.inflate(layoutInflater)
        setContentView(bd.root)


//        get intent data
        personCarry = intent.getParcelableExtra<PersonDataCarry>("dataPerson")!!
        datePick = intent.getSerializableExtra("dateAngket") as Date

        listDrug = ArrayList<DrugEntity>()
        bd.lvObat.visibility = View.GONE


        bd.btnAdd.setOnClickListener {
            addObat()
        }

        bd.btnNext.setOnClickListener {
            if (listDrug.isEmpty()) {
                Toast.makeText(this, "Please add drug!", Toast.LENGTH_SHORT).show()
            } else {
                insertDrug()
            }
        }

    }

    private fun insertDrug() {
        val db = PersonData.getInstance(this)
//        insert person data
        val personEntity = PersonEntity(
            0,
            name = personCarry.name,
            age = personCarry.age,
            address = personCarry.address,
            gender = personCarry.gender,
            date = datePick.toString()
        )

        lifecycleScope.launch(Dispatchers.IO){
            db.personDao().insert(personEntity)
            // iterate listDrug
            for (drug in listDrug) {
                Log.d(TAG, "insertDrug: ${drug.drug_name}")
                db?.drugDao()?.insert(drug)
            }

            withContext(Dispatchers.Main){
                Toast.makeText(this@AngketObatActivity, "Sukses menambahkan data", Toast.LENGTH_SHORT).show()
                goToMain()
            }

        }.invokeOnCompletion {
            if (it != null) {
                Toast.makeText(this, "Gagal menambahkan data, coba lagi!", Toast.LENGTH_SHORT).show()
            }
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

        val adapterLV = DrugListAdapter(this, listDrug)
        bd.lvObat.adapter = adapterLV

        // Update the ListView when the listDrug is updated
        adapterLV.notifyDataSetChanged()


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

                    listDrug.add(drug)
                    bd.lvObat.visibility = View.VISIBLE
                    adapterLV.notifyDataSetChanged()

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


    fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}