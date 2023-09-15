package com.aldin.ambetest.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aldin.ambetest.database.PersonDataCarry
import com.aldin.ambetest.databinding.ActivityAngketDateBinding
import java.util.*

class AngketDateActivity : AppCompatActivity() {
    private  lateinit var  personCarry: PersonDataCarry
    private lateinit var bd : ActivityAngketDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityAngketDateBinding.inflate(layoutInflater)
        setContentView(bd.root)

        personCarry = intent.getParcelableExtra<PersonDataCarry>("personData")!!

        // Get current date values
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Set up day picker
        val dayPicker = bd.dayPicker
        dayPicker.minValue = 1
        dayPicker.maxValue = 31
        dayPicker.value = currentDay

        // Set up month picker
        val monthPicker = bd.monthPicker
        monthPicker.minValue = 0
        monthPicker.maxValue = 11
        monthPicker.displayedValues = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        monthPicker.value = currentMonth

        // Set up year picker
        val yearPicker = bd.yearPicker
        yearPicker.wrapSelectorWheel = false
        yearPicker.minValue = currentYear - 100
        yearPicker.maxValue = currentYear
        yearPicker.value = currentYear

        // Set up submit button
        val submitButton = bd.btnNext
        submitButton.setOnClickListener {
            val day = dayPicker.value
            val month = monthPicker.value + 1 // Months are zero-indexed
            val year = yearPicker.value

            calendar.set(year, month-1, day)
            val selectedDate = calendar.time

            nextAngket(selectedDate)
            Toast.makeText(this, "Selected date: ${selectedDate}", Toast.LENGTH_SHORT).show()
        }


//        bd.btnNext.setOnClickListener {
//            Toast.makeText(this, "The person Name : ${personEntity?.name}", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun nextAngket(dateAngket: Date) {
        val intent = Intent(this, AngketObatActivity::class.java)
        intent.putExtra("dateAngket", dateAngket)
        intent.putExtra("dataPerson", personCarry)
        startActivity(intent)
        finish()
    }


}