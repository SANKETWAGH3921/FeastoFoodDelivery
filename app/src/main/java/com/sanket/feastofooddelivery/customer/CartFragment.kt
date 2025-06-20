package com.sanket.feastofooddelivery.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.adapter.CartAdapter
import com.sanket.feastofooddelivery.models.CartItemModel

class CartFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartList: ArrayList<CartItemModel>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var emptyText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerView = view.findViewById(R.id.cartRecyclerView)
        emptyText = view.findViewById(R.id.emptyCartText)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartList = ArrayList()
        cartAdapter = CartAdapter(cartList)
        recyclerView.adapter = cartAdapter

        loadCartItems()

        return view
    }

    private fun loadCartItems() {
        val cartRef = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child("Customers")
            .child("Cart")

        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartList.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CartItemModel::class.java)
                    item?.let { cartList.add(it) }
                }
                cartAdapter.notifyDataSetChanged()
                emptyText.visibility = if (cartList.isEmpty()) View.VISIBLE else View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                emptyText.text = "Failed to load cart"
                emptyText.visibility = View.VISIBLE
            }
        })
    }
}
