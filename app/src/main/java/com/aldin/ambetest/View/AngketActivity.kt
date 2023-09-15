package com.aldin.ambetest.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aldin.ambetest.database.PersonDataCarry
import com.aldin.ambetest.database.PersonEntity
import com.aldin.ambetest.databinding.ActivityAngketBinding

class AngketActivity : AppCompatActivity() {
    lateinit var bd : ActivityAngketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityAngketBinding.inflate(layoutInflater)
        setContentView(bd.root)


        bd.btnNext.setOnClickListener {
            getContent()
        }
    }

    private fun getContent() {
        if(
            bd.etNamaPasien.text.toString().isNotEmpty() &&
            bd.etUsiaPasien.text.toString().isNotEmpty() &&
            bd.etAlamatPasien.text.toString().isNotEmpty() &&
                    bd.rgGender.checkedRadioButtonId != -1
                ){
                var nama = bd.etNamaPasien.text.toString()
                var usia = bd.etUsiaPasien.text.toString().toInt()
                var alamat = bd.etAlamatPasien.text.toString()
                var gender = "Null"
                if(bd.rbMen.isSelected){
                    gender = "Laki - Laki"
                }else{
                    gender = "Perempuan"
                }

            val personCarry = PersonDataCarry(0, nama, usia, alamat, gender )
            nextAngket(personCarry)
        }else{
            Toast.makeText(this, "Tolong isikan semua data diri terlebih dahulu!", Toast.LENGTH_SHORT).show()
        }
    }

    fun nextAngket(personCarry: PersonDataCarry){
        val i = Intent(this, AngketDateActivity::class.java)
        i.putExtra("personData", personCarry)
        startActivity(i)
        finish()
    }
}