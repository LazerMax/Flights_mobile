package com.example.succulentus

import LoggingActivity
import android.os.Bundle

class MainActivity : LoggingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}