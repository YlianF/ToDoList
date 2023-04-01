package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import com.example.todolist.classes.Task
import com.example.todolist.handler.DatabaseHandler

class AddTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val returnbtn: Button = findViewById(R.id.returnButton)
        val addbtn: Button = findViewById(R.id.confirmButton)
        val datebtn: Button = findViewById(R.id.dateButton)

        var istaskdate = true

        //return button listener, to return to the main page
        returnbtn.setOnClickListener {
            val intent = Intent(this@AddTask, MainActivity::class.java)
            startActivity(intent)
        }

        //date button listener, to know if the task has a date
        datebtn.setOnClickListener {
            val date: DatePicker = findViewById(R.id.datePicker)

            if (date.isVisible) {
                datebtn.text = getString(R.string.adddate)
            } else {
                datebtn.text = getString(R.string.nodate)
            }

            date.isVisible = date.isVisible != true

            istaskdate = !istaskdate

        }

        //add button listener, to add a task
        addbtn.setOnClickListener {
            val databaseHandler = DatabaseHandler(this)

            val id = 0
            val title: TextView = findViewById(R.id.title)
            val date: DatePicker = findViewById(R.id.datePicker)
            val state = "todo"
            if (istaskdate && title.text.toString() != "") {
                val deadline = date.dayOfMonth.toString() + "-" + (date.month+1).toString() + "-" +date.year.toString()
                databaseHandler.addTask(Task(id,title.text.toString(), state, deadline))
            } else if (title.text.toString() != "") {
                databaseHandler.addTask(Task(id,title.text.toString(), state, ""))
            }

            val intent = Intent(this@AddTask, MainActivity::class.java)
            startActivity(intent)

        }

    }

}