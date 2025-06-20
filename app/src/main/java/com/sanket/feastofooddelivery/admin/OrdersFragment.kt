package com.sanket.feastofooddelivery.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.admin.adapter.OrderAdapter
import com.sanket.feastofooddelivery.models.OrderModel

class OrdersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderList: ArrayList<OrderModel>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        recyclerView = view.findViewById(R.id.ordersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        orderList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child("Admin")
            .child("Orders")

        fetchOrders()

        return view
    }

    private fun fetchOrders() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(OrderModel::class.java)
                    order?.let { orderList.add(it) }
                }
                recyclerView.adapter = OrderAdapter(orderList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
