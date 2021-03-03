package com.jguzmandev.demoroom.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jguzmandev.demoroom.R
import com.jguzmandev.demoroom.data.ProfessorTuple

class ProfessorArrayAdapter constructor(
    context: Context,
    private val professorList: List<ProfessorTuple>
) : ArrayAdapter<ProfessorTuple>(context, R.layout.custom_spinner, professorList) {
    init {
        (professorList as MutableList).add(0, ProfessorTuple(name = "<<=== Select a professor ===>>"))
    }

    override fun isEnabled(position: Int): Boolean {
        return when (position) {
            0 -> false
            else -> true
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById(R.id.tv_data_spinner) as TextView
        when(position){
            0 -> textView.setTextColor(Color.GRAY)
            else -> textView.setTextColor(Color.BLACK)
        }
        return view
    }
}