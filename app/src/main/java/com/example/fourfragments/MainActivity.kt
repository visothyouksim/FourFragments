package com.example.fourfragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonPlay).setOnClickListener{
            val intentNextActivity = Intent(this, SimonGame::class.java)
            startActivity(intentNextActivity)
        }

    }
}