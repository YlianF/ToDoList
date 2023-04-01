package com.example.todolist.handler

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.todolist.classes.Task

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskDatabase"
        private const val TABLE_CONTACTS = "TaskTable"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_STATE = "state"
        private const val KEY_DEADLINE = "deadline"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_DEADLINE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }


    //method to insert data
    fun addTask(task: Task):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, task.title)
        contentValues.put(KEY_STATE,task.state )
        contentValues.put(KEY_DEADLINE,task.deadline )
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    private fun getQueryViewTask(state: String): String {
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"

        if (state != "") {
            return "SELECT * FROM $TABLE_CONTACTS WHERE TRIM(state) = '"+state.trim()+"'"
        }
        return selectQuery
    }
    //method to read data
    fun viewTask(state: String):List<Task>{
        val taskList:ArrayList<Task> = ArrayList()


        val selectQuery = getQueryViewTask(state)

        val db = this.readableDatabase
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var title: String
        var state: String
        var deadline: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                state = cursor.getString(cursor.getColumnIndex("state"))
                deadline = cursor.getString(cursor.getColumnIndex("deadline"))
                val task= Task(id = id, title = title, state = state, deadline = deadline)
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return taskList
    }
    //method to update data
    fun updateTask(id: Int, newTitle: String):Int{

        val task = Task(0, "", "", "")
        val vt = viewTask("")
        for(i in vt){
            if (i.id == id) {
                task.id = i.id
                task.title = newTitle
                task.state = i.state
                task.deadline = i.deadline
            }
        }


        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.id)
        contentValues.put(KEY_TITLE, task.title)
        contentValues.put(KEY_STATE,task.state )
        contentValues.put(KEY_DEADLINE,task.deadline )

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+task.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun updateState(task: Task): Int {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.id)
        contentValues.put(KEY_TITLE, task.title)
        contentValues.put(KEY_STATE,"late" )
        contentValues.put(KEY_DEADLINE,task.deadline )

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+task.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    fun finishTask(id: Int):Int{

        val task = Task(0, "", "", "")
        val vt = viewTask("")
        for(i in vt){
            if (i.id == id) {
                task.id = i.id
                task.title = i.title
                task.state = "finished"
                task.deadline = i.deadline
            }
        }
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.id)
        contentValues.put(KEY_TITLE, task.title)
        contentValues.put(KEY_STATE,task.state )
        contentValues.put(KEY_DEADLINE,task.deadline )

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+task.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //method to delete data
    fun deleteTask(task: Task):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+task.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}