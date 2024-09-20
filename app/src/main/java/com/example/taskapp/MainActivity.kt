package com.example.taskapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button by its ID
        val addTask = findViewById<Button>(R.id.addTaskFABtn)

        // Set an onClickListener on the button
        addTask.setOnClickListener {
            // Create an Intent to navigate to SecondActivity
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }

        // Find the button by its ID
        val viewTask = findViewById<Button>(R.id.viewTaskFABtn)

        // Set an onClickListener on the button
        viewTask.setOnClickListener {
            // Create an Intent to navigate to SecondActivity
            val intent = Intent(this, ViewTask::class.java)
            startActivity(intent)
        }
    }
}