package com.sanket.feastofooddelivery.customer


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.sanket.feastofooddelivery.R

class CProfileFragment : Fragment() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUserId: TextView
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_c_profile, container, false)

        tvName = view.findViewById(R.id.tvName)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvUserId = view.findViewById(R.id.tvUserId)

        val adminUserId = "SANKET01" // If you're using Firebase Auth, replace with uid

        database = FirebaseDatabase.getInstance().getReference("Users/Customers/$adminUserId")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)
                    val userId = snapshot.child("userId").getValue(String::class.java)

                    tvName.text = "Name: ${name ?: "N/A"}"
                    tvEmail.text = "Email: ${email ?: "N/A"}"
                    tvUserId.text = "User ID: ${userId ?: "N/A"}"
                } else {
                    Toast.makeText(requireContext(), "Customer profile not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error loading profile", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
}
