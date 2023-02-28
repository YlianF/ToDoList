package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast

class TaskAdapter(items:ArrayList<Task>, ctx: Context) :
    ArrayAdapter<Task>(ctx,R.layout.list_item_recipe, items){

    //view holder is used to prevent findViewBy calls
    private class TaskItemViewHolder {
        internal var title: TextView? = null
        internal var state: TextView? = null
        internal var deadline: TextView? = null
    }

    override fun getView(i: Int, view : View?, viewGroup: ViewGroup): View {
        var view = view

        val viewHolder: TaskItemViewHolder

        if (view == null ) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item_recipe,viewGroup, false)

            viewHolder = TaskItemViewHolder()
            viewHolder.title = view!!.findViewById<View>(R.id.title) as TextView

        } else {
            //no need to call findViewById , can use existing ones from saved view holder
            viewHolder = view.tag as TaskItemViewHolder
        }

        val task = getItem(i)
        viewHolder.title!!.text = task!!.title
        //autre var je pense

        view.tag = viewHolder


        viewHolder.title!!.setOnClickListener {
            Toast.makeText(context, "The title is " + task!!.title,
                Toast.LENGTH_SHORT).show()
        }

        return view
    }
}