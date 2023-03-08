package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.example.todolist.classes.Task

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
            var title: TextView = findViewById(R.id.title)
            val date: DatePicker = findViewById(R.id.datePicker)
            val newTask = Task(0, title.text.toString(), "en cours",
                date.dayOfMonth.toString() + date.month)
        }

    }
}