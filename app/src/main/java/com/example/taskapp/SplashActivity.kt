package com.example.taskapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hero) // Set your hero.xml as the content view

        Handler(Looper.getMainLooper()).postDelayed({
            // Start the next activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Close the current activity (optional)
            finish()
        }, 1000) // 2000 milliseconds = 2 seconds
    }
}

