package com.sanket.feastofooddelivery.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.activities.AdminActivity
import com.sanket.feastofooddelivery.adapter.ItemAdapter
import com.sanket.feastofooddelivery.models.ItemModel

class AdminDashboardFragment : Fragment() {

    private lateinit var recyclerViewItems: RecyclerView
    private lateinit var navIcon: ImageView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var databaseReference: DatabaseReference
    private val itemList = mutableListOf<ItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)


        navIcon = view.findViewById(R.id.navimg)
        recyclerViewItems = view.findViewById(R.id.recyclerViewItems)


        navIcon.setOnClickListener {
            (activity as? AdminActivity)?.openDrawerFromFragment()
        }


        recyclerViewItems.layoutManager = LinearLayoutManager(requireContext())
        itemAdapter = ItemAdapter(itemList)
        recyclerViewItems.adapter = itemAdapter


        loadItemsFromFirebase()

        return view
    }

    private fun loadItemsFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("Users/Admin/Items")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (categorySnapshot in snapshot.children) {
                    for (itemSnapshot in categorySnapshot.children) {
                        val item = itemSnapshot.getValue(ItemModel::class.java)
                        item?.let { itemList.add(it) }
                    }
                }
                itemList.reverse() // Show latest items first
                itemAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load items", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
