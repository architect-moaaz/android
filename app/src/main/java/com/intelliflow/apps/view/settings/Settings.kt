package com.intelliflow.apps.view.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.intelliflow.apps.R


class Settings : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_settings, container, false)
        val miniAppsSpinner =view.findViewById(R.id.miniapps_spinner) as Spinner

        // Create an ArrayAdapter using the string array and a default spinner
        // Create an ArrayAdapter using the string array and a default spinner

        val miniAppsAdapter = context?.let {
            ArrayAdapter
                .createFromResource(
                    it, R.array.miniapps_array,
                    android.R.layout.simple_spinner_item
                )
        }
        miniAppsSpinner.adapter=miniAppsAdapter
        miniAppsSpinner.setSelection(0)
        return  view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
    }
}