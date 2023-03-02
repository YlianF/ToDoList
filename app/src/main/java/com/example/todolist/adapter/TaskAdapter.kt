package com.example.todolist.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import com.example.todolist.R
import com.example.todolist.classes.Task
class TaskAdapter(private val context: Activity,
                  private val title: Array<String>,
                  private val state: Array<String>,
                  private val deadline: Array<String>)
    : ArrayAdapter<String>(context, R.layout.list_item_recipe, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item_recipe, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val stateText = rowView.findViewById(R.id.state) as TextView
        val deadlineText = rowView.findViewById(R.id.deadline) as TextView

        val layoutItem : LinearLayout = rowView.findViewById(R.id.layoutItem)
        val btnCheck : AppCompatImageButton = rowView.findViewById(R.id.btnCheck)

        if ("${state[position]}" == "en cours") {
            layoutItem.background = ContextCompat.getDrawable(context,
                R.drawable.rounded_corners_light_purple
            )
        } else if ("${state[position]}" == "fini") {
            btnCheck.visibility = View.GONE
            layoutItem.background = ContextCompat.getDrawable(context,
                R.drawable.rounded_corners_green
            )
        } else if ("${state[position]}" == "en retard") {
            layoutItem.background = ContextCompat.getDrawable(context,
                R.drawable.rounded_corners_dark_purple
            )
        }


        titleText.text = "${title[position]}"
        stateText.text = "${state[position]}"
        deadlineText.text = "${deadline[position]}"
        return rowView
    }
}
