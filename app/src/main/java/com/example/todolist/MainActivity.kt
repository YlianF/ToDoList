package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.addButton)
        val laListView : ListView = findViewById(R.id.layoutListV)

        btn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTask::class.java)
            startActivity(intent)
        }

        val taskArray = arrayListOf(
            Task("1","en cours", "demain"),
            Task("22222222222222222222222222222222","fini", "demain"),
            Task("3","en cours", "demain"),
            Task("4","en cours", "demain"),
            Task("5","en retard", "demain"),
            Task("6","fini", "demain"),
            Task("777777777777777777777777777777777777777777777777777777777777777777777777777777","en cours", "demain"),
            Task("8","en retard", "demain"),
            Task("9","en cours", "demain"),
            Task("10","en cours", "demain"),
            Task("11","fini", "demain"))

        val adapter = TaskAdapter(taskArray, this)

        laListView.adapter = adapter
    }
}