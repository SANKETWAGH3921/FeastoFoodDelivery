package com.sanket.feastofooddelivery.customer



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.adapter.NotificationAdapter
import com.sanket.feastofooddelivery.models.NotificationModel


class NotificationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<NotificationModel>
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        recyclerView = view.findViewById(R.id.notificationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        notificationList = ArrayList()
        adapter = NotificationAdapter(notificationList)
        recyclerView.adapter = adapter

        fetchNotifications()

        return view
    }

    private fun fetchNotifications() {
        val ref = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child("Customers")
            .child("Notifications")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notificationList.clear()
                for (item in snapshot.children) {
                    val data = item.getValue(NotificationModel::class.java)
                    data?.let { notificationList.add(it) }
                }


                notificationList.reverse()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle if needed
            }
        })
    }
}
