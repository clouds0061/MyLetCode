package com.example.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myletcode.R
import com.example.myletcode.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    lateinit var binding  : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}