package com.aldin.ambetest.View

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.aldin.ambetest.R
import com.aldin.ambetest.database.PersonData
import com.aldin.ambetest.databinding.ActivityDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {

    private lateinit var database: PersonData
    private lateinit var bd : ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bd.root)

        database = PersonData.getInstance(this)
//        val personData = database.personDao().getAllMyEntities()
//        for (person in personData){
//            Log.d("TAG", "Database Value Person: ${person.id}, ${person.name}, ${person.age}")
//        }

        showData()

        bd.btnValidate.setOnClickListener {
            lifecycleScope.launch {
                val id = bd.etID.text.toString().toInt()
                deleteByID(id)
            }
        }


//        if (database == null){
//            database = Room.databaseBuilder(
//                this,
//                PersonData::class.java,
//                "database_my"
//            ).build()
//        }



    }

    fun showData(){
        lifecycleScope.launch {
            val personData = withContext(Dispatchers.IO) {
                database.personDao().getAllMyEntities()
            }
            var cr = ""
            for (person in personData){
                cr += "\n ${person.id} | ${person.name}"
                Log.d("TAG", "Database Value Person: ${person.id}, ${person.name}, ${person.age}")
            }

            bd.tvMain.text = cr
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