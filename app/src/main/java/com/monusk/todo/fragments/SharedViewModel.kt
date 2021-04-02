package com.monusk.todo.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.monusk.todo.R
import com.monusk.todo.data.models.Priority
import com.monusk.todo.data.models.ToDoData
import kotlinx.android.synthetic.main.fragment_add.*

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>) {
        emptyDatabase.value = toDoData.isEmpty()
    }

    val listner: AdapterView.OnItemSelectedListener = object :
    AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long) {

            when(position) {
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}

            }

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }


    fun verifyDataFromUser(title: String, description: String): Boolean{
        return (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(description))
    }

    fun parsePriority(priority: String): Priority {
        return when(priority) {
            "HIGH PRIORITY" -> {
                Priority.HIGH}
            "MEDIUM PRIORITY" -> {
                Priority.MEDIUM}
            "LOW PRIORITY" -> {
                Priority.LOW}
            else -> Priority.LOW
        }
    }


}