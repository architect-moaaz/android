package com.intelliflow.apps.view.recentactions

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*


class SelectDateFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

//implements DatePickerDialog.OnDateSetListener  {

   lateinit var  edit: EditText


    fun SelectDateFragment(edit: EditText) {
        this.edit = edit
    }
//
//
//override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {
//        val calendar: Calendar = Calendar.getInstance()
//        val yy: Int = calendar.get(Calendar.YEAR)
//        val mm: Int = calendar.get(Calendar.MONTH)
//        val dd: Int = calendar.get(Calendar.DAY_OF_MONTH)
//        return activity?.let { DatePickerDialog(it, this, yy, mm, dd) }
//    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {
//// Use the current date as the default date in the picker
//        val c = Calendar.getInstance()
//        val year = c[Calendar.YEAR]
//        val month = c[Calendar.MONTH]
//        val day = c[Calendar.DAY_OF_MONTH]
//
//// Create a new instance of DatePickerDialog and return it
//        return activity?.let { DatePickerDialog(it, this, year, month, day) }
//    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c[year, month] = day
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = sdf.format(c.time)
    }


//    var datePicker = DatePicker(activity)
//    val layoutParams = LinearLayout.LayoutParams(
//        ViewGroup.LayoutParams.MATCH_PARENT,
//        ViewGroup.LayoutParams.MATCH_PARENT)



  //  datePicker.

  //  datePicker.layoutParams = layoutParams

//
//    val linearLayout = findViewById<LinearLayout>(R.id.linear_layout)
//    // add datePicker in LinearLayout
//    linearLayout?.addView(datePicker)
//
//    val today = Calendar.getInstance()
//    datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
//    today.get(Calendar.DAY_OF_MONTH)
//
//    ) { view, year, month, day ->
//        val month = month + 1
//        val msg = "You Selected: $day/$month/$year"
//        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
//
//    }



}