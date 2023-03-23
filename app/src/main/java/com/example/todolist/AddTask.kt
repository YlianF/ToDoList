package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.todolist.classes.Task
import com.example.todolist.handler.DatabaseHandler

class AddTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val returnbtn: Button = findViewById(R.id.returnButton)
        val addbtn: Button = findViewById(R.id.confirmButton)

        returnbtn.setOnClickListener {
            val intent = Intent(this@AddTask, MainActivity::class.java)
            startActivity(intent)
        }

        addbtn.setOnClickListener {
            val id = 0
            var title: TextView = findViewById(R.id.title)
            val date: DatePicker = findViewById(R.id.datePicker)
            val state = "en cours"
            val deadline = date.dayOfMonth.toString() + "-" + (date.month+1).toString() + "-" +date.year.toString()
            val databaseHandler = DatabaseHandler(this)
            databaseHandler.addTask(Task(id,title.text.toString(), state, deadline))
        }

    }

}