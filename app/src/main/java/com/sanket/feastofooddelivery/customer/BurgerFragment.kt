package com.sanket.feastofooddelivery.customer

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.adapter.ItemAdapter
import com.sanket.feastofooddelivery.models.ItemModel

class BurgerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private val itemList = mutableListOf<ItemModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_burger, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewBurger)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ItemAdapter(itemList) { item, imageResId ->
            val intent = Intent(requireContext(), SelectedItemActivity::class.java)
            intent.putExtra("itemName", item.itemName)
            intent.putExtra("description", item.description)
            intent.putExtra("price", item.price)
            intent.putExtra("imageResId", imageResId)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        loadItems("Burger")
        return view
    }

    private fun loadItems(category: String) {
        FirebaseDatabase.getInstance().getReference("Users/Admin/Items/$category")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    itemList.clear()
                    for (item in snapshot.children) {
                        val model = item.getValue(ItemModel::class.java)
                        model?.let { itemList.add(it) }
                    }
                    itemList.reverse()
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to load $category items", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
