package com.aldin.ambetest.View

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aldin.ambetest.MainActivity
import com.aldin.ambetest.R
import com.aldin.ambetest.databinding.ActivityWelcomingBinding
import com.aldin.ambetest.databinding.LayoutWelcoming7Binding
import com.omni.onboardingscreen.domain.OnBoardingPrefManager

class WelcomingActivity : AppCompatActivity() {

    private val prefs : SharedPreferences by lazy {
        getPreferences(MODE_PRIVATE)
    }
    private var count = 1
    private lateinit var bd : ActivityWelcomingBinding
    private lateinit var prefMan : OnBoardingPrefManager
    lateinit var bind7 : LayoutWelcoming7Binding
//    private lateinit var ly7 : LayoutWelcoming7Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd  = ActivityWelcomingBinding.inflate(layoutInflater)
        setContentView(bd.root)

//        bd.vstub7.inflate()
//        ly7 = LayoutWelcoming7Binding.inflate(layoutInflater,bd.vstub7,false)

        bd.vstub2.visibility = View.GONE
//        val inflatedLayout = bd.vstub7.inflate()
//        bind7 = LayoutWelcoming7Binding.bind(inflatedLayout)
//        bd.vstub7.visibility = View.GONE

//        bind7.tvNametitle.text = "It's tested"

//        bd.vstub6.inflate(layoutInflater)


        prefMan = OnBoardingPrefManager(this)


        bd.vstub.inflate()
        bd.btnNextWelcoming.setOnClickListener {
            showStub(count)
            count++
        }





//        bd.include

    }

    fun showStub(count :Int){
        when(count){
            1 -> {
                bd.vstub.visibility = View.GONE
                bd.vstub2.inflate()
                bd.vstub2.visibility = View.VISIBLE
            }
            2 -> {
                bd.vstub2.visibility = View.GONE
                bd.vstub3.inflate()
                bd.vstub3.visibility = View.VISIBLE
            }
            3 -> {
                bd.vstub3.visibility = View.GONE
                bd.vstub4.inflate()
                bd.vstub4.visibility = View.VISIBLE
            }
            4 -> {
                bd.vstub4.visibility = View.GONE
                bd.vstub5.inflate()
                bd.vstub5.visibility = View.VISIBLE
            }
            5 -> {
                bd.vstub5.visibility = View.GONE
                bd.vstub6.inflate()
                bd.vstub6.visibility = View.VISIBLE
            }
            6 -> {

                prefMan.isFirstTimeLaunch = false
                BackToMain()
            }
        }

    }


    private fun BackToMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun ContinueTo(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}