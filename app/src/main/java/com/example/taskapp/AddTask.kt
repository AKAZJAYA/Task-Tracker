package com.example.taskapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskapp.databinding.ActivityAddTaskBinding
import com.example.taskapp.models.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddTask : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("TaskData", Context.MODE_PRIVATE)

        binding.saveTaskBtn.setOnClickListener {

            val taskTitle = binding.edTaskTitle.text.toString()
            val taskDis = binding.edTaskDesc.text.toString()

            if (taskTitle.isNotEmpty() && taskDis.isNotEmpty()) {
                val taskList = loadTasks() // Load existing tasks
                val newTask = Task(taskTitle, taskDis, System.currentTimeMillis())
                taskList.add(newTask) // Add new task to the list

                // Save updated list back to SharedPreferences
                val editor = sharedPreferences.edit()
                val taskListJson = gson.toJson(taskList)
                editor.putString("taskList", taskListJson)
                editor.apply()

                Toast.makeText(this, "Task has been successfully stored.", Toast.LENGTH_SHORT)
                    .show()

                // Clear input fields
                binding.edTaskTitle.text?.clear()
                binding.edTaskDesc.text?.clear()
            } else {
                Toast.makeText(this, "Please fill in both fields.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.closeImg.setOnClickListener {
            // Create an Intent to navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Finish AddTask activity if you don't want to keep it in the back stack
        }
    }

    // Function to load existing tasks from SharedPreferences
    private fun loadTasks(): MutableList<Task> {
        val taskListJson = sharedPreferences.getString("taskList", null)
        return if (!taskListJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            gson.fromJson(taskListJson, type)
        } else {
            mutableListOf()
        }
    }


}