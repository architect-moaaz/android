package com.intelliflow.apps.view.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.intelliflow.apps.R
import com.intelliflow.apps.adapter.CustomExpandableListAdapter


class Notification_Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var expandableListView: ExpandableListView? = null
    var data: HashMap<String, List<String>>? =null
    private var titleList: List<String>? = null
    private var adapter: ExpandableListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.notification_settings, container, false)


        expandableListView = view.findViewById(R.id.expendableList)

        if (expandableListView != null) {

            data = HashMap<String, List<String>>()

            val miniApps: MutableList<String> =
                ArrayList()
            miniApps.add("Leave Application")
            miniApps.add("Bank Loan App")
            miniApps.add("Medical History")


            data?.set("Mini Apps Settings ", miniApps)

            val listData = data

            titleList =
                ArrayList(data?.keys)
            adapter = data?.let {
                context?.let { it1 ->
                    CustomExpandableListAdapter(
                        it1,

                        titleList as ArrayList<String>,
                        it
                    )
                }
            }
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
//                Toast.makeText(
//                   context,
//                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
//                Toast.makeText(
//                    context,
//                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
//                Toast.makeText(
//                    context,
//                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + data?.get(
//                        (
//                                titleList as
//                                        ArrayList<String>
//                                )
//                                [groupPosition]
//                    )!!.get(
//                        childPosition
//                    ),
//                    Toast.LENGTH_SHORT
//                ).show()
                false
            }
        }

      //  val miniAppsSpinner =view.findViewById(R.id.miniapps_spinner) as Spinner

        // Create an ArrayAdapter using the string array and a default spinner

        // Create an ArrayAdapter using the string array and a default spinner
//        val miniAppsAdapter = context?.let {
//            ArrayAdapter
//                .createFromResource(
//                    it, R.array.miniapps_array,
//                    android.R.layout.simple_spinner_item
//                )
//        }
//
//        miniAppsSpinner.adapter=miniAppsAdapter
//
//        miniAppsSpinner.setSelection(0)

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

