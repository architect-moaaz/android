package com.intelliflow.apps.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intelliflow.apps.R
import com.intelliflow.apps.adapter.Notification_adapter
import com.intelliflow.apps.databinding.FragmentSecondBinding
import com.intelliflow.apps.databinding.FragmentTodoBinding
import com.intelliflow.apps.model.Notificationvar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TodoFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationAdapter: Notification_adapter
    private var dataList = mutableListOf<Notificationvar>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo, container, false)
        recyclerView = view.findViewById(R.id.notification_recycler_view);
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        notificationAdapter = Notification_adapter()
        recyclerView.adapter = notificationAdapter
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded .  Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded .  Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL   has been uploaded . Check Now "))


        notificationAdapter.setDataList(dataList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonTodo.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
     //   _binding = null
    }
}