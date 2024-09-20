package com.example.taskapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.databinding.ActivityViewTaskBinding
import com.example.taskapp.models.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ViewTask : AppCompatActivity() {

    private lateinit var binding: ActivityViewTaskBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("TaskData", Context.MODE_PRIVATE)

        // Set up RecyclerView
        binding.taskRV.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList, this::deleteTask, this::updateTask) // Pass the updateTask function
        binding.taskRV.adapter = taskAdapter

        // Load tasks from SharedPreferences
        loadTasks()
    }

    private fun loadTasks() {
        val taskListJson = sharedPreferences.getString("taskList", null)
        if (!taskListJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            val tasks: MutableList<Task> = gson.fromJson(taskListJson, type)
            taskList.addAll(tasks)
            taskAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteTask(position: Int) {
        // Remove task from list
        taskList.removeAt(position)

        // Save updated task list back to SharedPreferences
        saveTaskList()

        // Notify adapter and show Toast message
        taskAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Task deleted successfully.", Toast.LENGTH_SHORT).show()

        // If no tasks are left, show a different message
        if (taskList.isEmpty()) {
            Toast.makeText(this, "No tasks available.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTask(position: Int, updatedTask: Task) {
        // Update task in list
        taskList[position] = updatedTask

        // Save updated task list back to SharedPreferences
        saveTaskList()

        // Notify adapter and show Toast message
        taskAdapter.notifyItemChanged(position)
        Toast.makeText(this, "Task updated successfully.", Toast.LENGTH_SHORT).show()
    }

    private fun saveTaskList() {
        val editor = sharedPreferences.edit()
        val taskListJson = gson.toJson(taskList)
        editor.putString("taskList", taskListJson)
        editor.apply()
    }
}