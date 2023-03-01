package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.addButton)
        val laListView : ListView = findViewById(R.id.layoutListV)

        btn.setOnClickListener() {
            val intent = Intent(this@MainActivity, AddTask::class.java)
            startActivity(intent)
        }

        val TaskArray = arrayListOf<Task>(
            Task("Outer Banks","en cours", "demain"),
            Task("One Piece","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("le titre est beaucoup trop long la lorem ipsum dorime iterimo dorime","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("faire la vaisselle","en cours", "demain"),
            Task("faire le tp de virtualisation","en cours", "demain"),
            Task("faire le ménage","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"),
            Task("Avatar 2","en cours", "demain"))

        val adapter = TaskAdapter(TaskArray, this)

        laListView.adapter = adapter
    }
}