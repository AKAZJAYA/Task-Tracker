package com.example.taskapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.databinding.UpdateTaskDialogBinding
import com.example.taskapp.databinding.ViewTaskLayoutBinding
import com.example.taskapp.models.Task
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class TaskAdapter(
    private var taskList: MutableList<Task>,
    private val deleteTask: (Int) -> Unit,
    private val updateTask: (Int, Task) -> Unit // Pass update callback
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ViewTaskLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ViewTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.titleTxt.text = task.title
        holder.binding.descTxt.text = task.description

        // Format and set the date
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(task.dateAdded))
        holder.binding.dateTxt.text = date

        // Handle delete button click
        holder.binding.deleteImg.setOnClickListener {
            deleteTask(position)
        }

        // Handle edit button click
        holder.binding.editImg.setOnClickListener {
            showEditTaskDialog(holder.binding.root.context, task, position)
        }
    }

    override fun getItemCount(): Int = taskList.size

    // Update the task list when data changes
    fun updateTasks(newTasks: MutableList<Task>) {
        taskList = newTasks
        notifyDataSetChanged()
    }

    // Filter the task list based on the search query
    fun filterTasks(query: String) {
        // Add filtering logic if needed
        // Make sure to handle filteredTaskList properly in the adapter
    }

    private fun showEditTaskDialog(context: Context, task: Task, position: Int) {
        val dialogBinding = UpdateTaskDialogBinding.inflate(LayoutInflater.from(context))

        // Prepopulate the dialog with the task's current data
        dialogBinding.edTaskTitle.setText(task.title)
        dialogBinding.edTaskDesc.setText(task.description)

        // Build the dialog
        val dialog = AlertDialog.Builder(context)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        // Handle the update task button click
        dialogBinding.updateTaskBtn.setOnClickListener {
            val updatedTitle = dialogBinding.edTaskTitle.text.toString()
            val updatedDesc = dialogBinding.edTaskDesc.text.toString()

            if (updatedTitle.isNotEmpty() && updatedDesc.isNotEmpty()) {
                val updatedTask = Task(updatedTitle, updatedDesc, System.currentTimeMillis())
                updateTask(position, updatedTask) // Invoke update callback
                Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle the close button click
        dialogBinding.closeImg.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}