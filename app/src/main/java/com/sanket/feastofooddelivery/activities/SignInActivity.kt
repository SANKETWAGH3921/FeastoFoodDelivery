package com.sanket.feastofooddelivery.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.sanket.feastofooddelivery.R

class SignInActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val etUserIdSignin = findViewById<EditText>(R.id.etUserIdSignin)
        val etPasswordSignin = findViewById<EditText>(R.id.etPasswordSignin)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val tvBackToSignup = findViewById<TextView>(R.id.tvbacktosignup)

        database = FirebaseDatabase.getInstance().reference.child("Users")

        btnSignIn.setOnClickListener {
            val userId = etUserIdSignin.text.toString().trim()
            val inputPassword = etPasswordSignin.text.toString().trim()

            if (userId.isNotEmpty() && inputPassword.isNotEmpty()) {
                val adminRef = database.child("Admin").child(userId)
                val customerRef = database.child("Customers").child(userId)

                adminRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val dbPassword = snapshot.child("password").value.toString()
                            if (dbPassword == inputPassword) {
                                val intent = Intent(this@SignInActivity, AdminActivity::class.java)
                                intent.putExtra("userId", userId)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@SignInActivity, "Incorrect password!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            customerRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        val dbPassword = snapshot.child("password").value.toString()
                                        if (dbPassword == inputPassword) {
                                            val intent = Intent(this@SignInActivity, CustomerActivity::class.java)
                                            intent.putExtra("userId", userId)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            Toast.makeText(this@SignInActivity, "Incorrect password!", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(this@SignInActivity, "User ID not found!", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(this@SignInActivity, "Database error!", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@SignInActivity, "Database error!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter both User ID and Password!", Toast.LENGTH_SHORT).show()
            }
        }

        tvBackToSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}
