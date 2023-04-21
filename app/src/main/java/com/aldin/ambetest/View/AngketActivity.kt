package com.aldin.ambetest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldin.ambetest.databinding.ActivityAngketBinding

class AngketActivity : AppCompatActivity() {
    lateinit var bd : ActivityAngketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityAngketBinding.inflate(layoutInflater)
        setContentView(bd.root)



    }
}