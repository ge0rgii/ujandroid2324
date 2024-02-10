package com.simpletodos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// main activity of the app
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}