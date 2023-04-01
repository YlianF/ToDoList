package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.classes.Task
import com.example.todolist.handler.DatabaseHandler
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.addButton)
        val chipgrp: ChipGroup = findViewById(R.id.chips)

        btn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTask::class.java)
            startActivity(intent)
        }

        val taskArray = viewRecord()

        val image: ImageView = findViewById(R.id.imageView)
        image.isVisible = taskArray.isEmpty()


    }

    private fun viewRecord(): List<Task> {

        val databaseHandler = DatabaseHandler(this)

        val taskArray: List<Task> = databaseHandler.viewTask()



        val taskArrayId = Array(taskArray.size){"0"}
        val taskArrayTitle = Array(taskArray.size){"null"}
        val taskArrayState = Array(taskArray.size){"null"}
        val taskArrayDeadline = Array(taskArray.size){"null"}
        for((index, t) in taskArray.withIndex()){
            taskArrayId[index] = t.id.toString()
            taskArrayTitle[index] = t.title
            taskArrayState[index] = t.state
            taskArrayDeadline[index] = t.deadline

            //update late tasks of task with deadline
            if (taskArrayDeadline[index] != "") {
                val dateFormated = taskArrayDeadline[index]
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val minutesDifference = TimeUnit.MINUTES.convert((dateFormat.parse(dateFormated).time - Date().time), TimeUnit.MILLISECONDS)
                if (taskArrayState[index] =="en cours" && minutesDifference < 0) {
                    taskArrayState[index] = "en retard"
                    databaseHandler.updateState(Task(taskArrayId[index].toInt(), taskArrayTitle[index], taskArrayState[index], taskArrayDeadline[index]))
                }
            }

        }
        //creating custom ArrayAdapter
        val myListAdapter = TaskAdapter(this,taskArrayId,taskArrayTitle,taskArrayState,taskArrayDeadline)
        findViewById<ListView>(R.id.layoutListV).adapter = myListAdapter

        return taskArray
    }


    fun updateRecord(view: View){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtTitle = dialogView.findViewById(R.id.updateTitle) as EditText

        val separated: List<String> = view.contentDescription.split(" ")
        val updateId = separated[2]

        dialogBuilder.setTitle("Update Task")
        dialogBuilder.setMessage("Enter the new name below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateTitle = edtTitle.text.toString()

            //creating the instance of DatabaseHandler class
            val databaseHandler = DatabaseHandler(this)
            if(updateTitle.trim()!=""){
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = databaseHandler.updateTask(Integer.parseInt(updateId),updateTitle)
                if(status > -1){
                    Toast.makeText(applicationContext,"title updated",Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"error",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

    //method for deleting records based on id
    fun deleteRecord(view: View){

        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val separated: List<String> = view.contentDescription.split(" ")
        val deleteId = separated[2]

        dialogBuilder.setTitle("Delete Task")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler = DatabaseHandler(this)
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHandler.deleteTask(Task(Integer.parseInt(deleteId),"","", ""))
                if(status > -1){
                    Toast.makeText(applicationContext,"Task " + Integer.parseInt(deleteId).toString() + " deleted",
                        Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"error",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }


    fun finishTask(view: View){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.finish_dialog, null)
        dialogBuilder.setView(dialogView)

        val separated: List<String> = view.contentDescription.split(" ")
        val id = separated[2]

        dialogBuilder.setTitle("Did you finished ?")
        dialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler= DatabaseHandler(this)
            val status = databaseHandler.finishTask(Integer.parseInt(id))
            if(status > -1){
                Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
                viewRecord()
            }

        })
        dialogBuilder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

}