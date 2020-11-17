package com.example.bubble_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btb = findViewById<Button> (R.id.btn)
        btn?.setOnClickListener ()
        {
            Toast.makeText(this@MainActivity, R.string.message, Toast.LENGTH_SHORT).show()
        }
    }
}