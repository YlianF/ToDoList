package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
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
        val laListView : ListView = findViewById(R.id.layoutListV)

        btn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTask::class.java)
            startActivity(intent)
        }



        /*
        val taskArray = arrayListOf(
            Task(1, "1","en cours", "demain"),
            Task(2,"22222222222222222222222222222222","fini", "demain"),
            Task(3,"3","en cours", "demain"),
            Task(4,"4","en cours", "demain"),
            Task(5,"5","en retard", "demain"),
            Task(6,"6","fini", "demain"),
            Task(7,"777777777777777777777777777777777777777777777777777777777777777777777777777777","en cours", "demain"),
            Task(8,"8","en retard", "demain"),
            Task(9,"9","en cours", "demain"),
            Task(10,"10","en cours", "demain"),
            Task(11,"11","fini", "demain")
        )

        val adapter = TaskAdapter(taskArray, this)
        laListView.adapter = adapter

         */
        var tasktest = Task(1, "la tache de la destinée radieuse", "en cours", "maintenant")
        saveRecord()
        viewRecord()
    }




    //method for saving records in database

    fun saveRecord(){
        //val id = findViewById<EditText>(R.id.u_id).text.toString()
        //val name = findViewById<EditText>(R.id.u_name).text.toString()
        //val email = findViewById<EditText>(R.id.u_email).text.toString()
        val id = "3"
        val title = "la tache du test3"
        val state = "fini"
        val deadline = "maintenant"
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
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

    //method for read records from database in ListView
    fun viewRecord(){
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val taskArray: List<Task> = databaseHandler.viewTask()
        val taskArrayId = Array<String>(taskArray.size){"0"}
        val taskArrayTitle = Array<String>(taskArray.size){"null"}
        val taskArrayState = Array<String>(taskArray.size){"null"}
        val taskArrayDeadline = Array<String>(taskArray.size){"null"}
        var index = 0
        for(t in taskArray){
            taskArrayId[index] = t.id.toString()
            taskArrayTitle[index] = t.title
            taskArrayState[index] = t.state
            taskArrayDeadline[index] = t.deadline
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = TaskAdapter(this,taskArrayTitle,taskArrayState,taskArrayDeadline)
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
    fun deleteRecord(view: View, id: String){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHandler.deleteTask(Task(Integer.parseInt(deleteId),"","", ""))
                if(status > -1){
                    Toast.makeText(applicationContext,"record deleted",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

    fun deleteRecord(view: View) {}

}