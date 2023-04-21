package com.aldin.ambetest

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.aldin.ambetest.View.AngketActivity
import com.aldin.ambetest.View.DashboardActivity
import com.aldin.ambetest.View.WelcomingActivity
import com.aldin.ambetest.database.PersonData
import com.aldin.ambetest.database.PersonEntity
import com.omni.onboardingscreen.domain.OnBoardingPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT : Long = 20000
    private lateinit var context : Context
    private lateinit var prefMan : OnBoardingPrefManager
    private lateinit var database : PersonData
    private val prefs : SharedPreferences by lazy {
        getPreferences(MODE_PRIVATE)
    }

//    private val prefMan : OnBoardingPrefManager = OnBoardingPrefManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        prefMan = OnBoardingPrefManager(context)

        //create database
        database = PersonData.getInstance(this)

        var x = prefMan.isFirstTimeLaunch
        Log.d(ContentValues.TAG, "Main: $x")

//        prefMan.isFirstTimeLaunch = false
        var status = prefMan.isFirstTimeLaunch

//        if (!status){
//            Handler().postDelayed({
//                startActivity(Intent(this, DashboardActivity::class.java))
//                finish()
//            }, SPLASH_TIME_OUT)
//        }else{
//            startActivity(Intent(this, WelcomingActivity::class.java))
//            finish()
//        }

        if (!status){
            lifecycleScope.launch(Dispatchers.IO){
                val theData : List<PersonEntity> = getAllMyEntities()
                Log.d("TAG", "the data : ==> ${theData} ")

                for (dt in theData){
                    Log.d("TAG", "the data : ==> ${dt} ")
                }
                if(theData.isNotEmpty()){
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
                    }
                }else{
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@MainActivity, AngketActivity::class.java))
                    }
                }
            }
            finish()
        }else{
            startActivity(Intent(this, WelcomingActivity::class.java))
            finish()
        }

//        if (!prefs.getBoolean("firstTime", true)){
//            Handler().postDelayed({
//                startActivity(Intent(this, DashboardActivity::class.java))
//                finish()
//            }, SPLASH_TIME_OUT)
//        }else{
//            startActivity(Intent(this, WelcomingActivity::class.java))
//            finish()
//        }


    }

    suspend fun getAllMyEntities(): List<PersonEntity> = withContext(Dispatchers.IO) {
        database.personDao().getAllMyEntities()
    }

//    private fun insertData() {
//        val randomNumber = Random.nextInt(100)
//        val data = PersonEntity(0,"Aldin $randomNumber", 20)
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            insertPerson(data, database)
//        }
//    }

    suspend fun checkData(){
        withContext(Dispatchers.IO){

        }
    }

  suspend fun insertPerson(person: PersonEntity, db: PersonData) {
        withContext(Dispatchers.IO) {
            db.personDao().insert(person)
        }
    }

}