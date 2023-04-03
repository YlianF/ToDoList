package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.classes.Task
import com.example.todolist.handler.DatabaseHandler
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnadd: Button = findViewById(R.id.addButton)

        val btntodo: Button = findViewById(R.id.btn1)
        val btnlate: Button = findViewById(R.id.btn3)
        val btnfinished: Button = findViewById(R.id.btn2)
        val btnall: Button = findViewById(R.id.btn4)

        val imageHaut = findViewById<ImageView>(R.id.image_view_anime)
        imageHaut.visibility = ImageView.INVISIBLE

        //go to add task page
        btnadd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTask::class.java)
            startActivity(intent)
        }

        //view task when main page is loading
        val taskArray = viewRecord("")

        //making visible and invisible confettiman if there are no tasks
        val image: ImageView = findViewById(R.id.imageView)
        image.isVisible = taskArray.isEmpty()

        //all listeners for sorting tasks :
        btnall.setOnClickListener() {
            viewRecord("")
        }
        btntodo.setOnClickListener() {
            viewRecord("todo")
        }
        btnlate.setOnClickListener() {
            viewRecord("late")
        }
        btnfinished.setOnClickListener() {
            viewRecord("finished")
        }

    }

    private fun viewRecord(state: String): List<Task> {

        val databaseHandler = DatabaseHandler(this)

        val taskArray: List<Task> = databaseHandler.viewTask(state)

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
                if (taskArrayState[index] =="todo" && minutesDifference < 0) {
                    taskArrayState[index] = "late"
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
                    viewRecord("")
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
                    viewRecord("")
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

        dialogBuilder.setTitle("Did you finish ?")
        dialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler= DatabaseHandler(this)
            val status = databaseHandler.finishTask(Integer.parseInt(id))
            if(status > -1){
                Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
                viewRecord("")
                val imageView = findViewById<ImageView>(R.id.image_view_anime)
                val musique = R.raw.animation

                playAnimation(imageView, musique, 200L, 3000L) //pour les temps
            }

        })
        dialogBuilder.setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()

        b.show()
    }

    private fun playAnimation(
        imageView: ImageView,
        musique: Int,
        delayShow: Long,
        delayStop: Long
    ) {
        val mediaPlayer = MediaPlayer.create(this, musique)
        val handler = Handler()

        handler.postDelayed({
            imageView.visibility = ImageView.VISIBLE
            mediaPlayer.start()
        }, delayShow)

        handler.postDelayed({
            imageView.visibility = ImageView.INVISIBLE
            mediaPlayer.stop()
            mediaPlayer.release()
        }, delayStop)
    }

}