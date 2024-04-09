package com.intelliflow.apps.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intelliflow.apps.R
import com.intelliflow.apps.adapter.Notification_adapter
import com.intelliflow.apps.model.Notificationvar


class Notification : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationAdapter: Notification_adapter
    private var dataList = mutableListOf<Notificationvar>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = view.findViewById(R.id.notification_recycler_view);
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        notificationAdapter = Notification_adapter()
        recyclerView.adapter = notificationAdapter
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))
        dataList.add(Notificationvar(R.drawable.ic_notification_card,"Hey! Payslip for the month APRIL 2022  has been uploaded"))


        notificationAdapter.setDataList(dataList)
        return view
    }
    }
