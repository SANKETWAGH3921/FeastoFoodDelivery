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
import com.sanket.feastofooddelivery.admin.adapter.OrderAdapter
import com.sanket.feastofooddelivery.models.OrderModel


class MyOrdersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter
    private lateinit var orderList: ArrayList<OrderModel>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myorders, container, false)

        recyclerView = view.findViewById(R.id.myOrdersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        orderList = ArrayList()

        // Fetch all orders from: Users -> Admin -> Orders
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
                adapter = OrderAdapter(orderList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Optional: handle error
            }
        })
    }
}
