package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.classes.Task
import com.example.todolist.handler.DatabaseHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.addButton)

        btn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTask::class.java)
            startActivity(intent)
        }

        saveRecord()
        saveRecord()
        viewRecord()
    }




    //method for saving records in database

    private fun saveRecord(){
        //val id = findViewById<EditText>(R.id.u_id).text.toString()
        //val name = findViewById<EditText>(R.id.u_name).text.toString()
        //val email = findViewById<EditText>(R.id.u_email).text.toString()
        val id = "0"
        val title = "la tache du test1"
        val state = "en cours"
        val deadline = "maintenant"
        val databaseHandler = DatabaseHandler(this)
        if(id.trim()!="" && title.trim()!="" && state.trim()!="" && deadline.trim() != ""){
            val status = databaseHandler.addTask(Task(Integer.parseInt(id),title, state, deadline))
            /*if(status > -1){
                Toast.makeText(applicationContext,"record save",Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.u_id).text.clear()
                findViewById<EditText>(R.id.u_name).text.clear()
                findViewById<EditText>(R.id.u_email).text.clear()
            }

             */
        }else{
            Toast.makeText(applicationContext,"title must not be blank",Toast.LENGTH_LONG).show()
        }

    }


    private fun viewRecord(){

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
        }
        //creating custom ArrayAdapter
        val myListAdapter = TaskAdapter(this,taskArrayId,taskArrayTitle,taskArrayState,taskArrayDeadline)
        findViewById<ListView>(R.id.layoutListV).adapter = myListAdapter
    }
    //method for updating records based on user id
    /*
    fun updateRecord(view: View){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText

        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateEmail = edtEmail.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId),updateName, updateEmail))
                if(status > -1){
                    Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
    */
    //method for deleting records based on id
    fun deleteRecord(view: View){

        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val separated: List<String> = view.contentDescription.split(" ")
        val deleteId = separated[2]

        val allTasks = viewRecord()


        dialogBuilder.setTitle("Delete Record")
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
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val separated: List<String> = view.contentDescription.split(" ")
        val id = separated[2]

        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler= DatabaseHandler(this)
            val status = databaseHandler.finishTask(Integer.parseInt(id))
            if(status > -1){
                Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
                viewRecord()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

}