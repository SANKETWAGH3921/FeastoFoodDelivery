
package com.sanket.feastofooddelivery.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.sanket.feastofooddelivery.R


class SignUpActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Find all views by ID
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEMail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etUserId = findViewById<EditText>(R.id.etUserId)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvAlreadyRegistered = findViewById<TextView>(R.id.tvAlreadyRegistered)

        val rbAdmin = findViewById<RadioButton>(R.id.rbAdmin)
        val rbCustomer = findViewById<RadioButton>(R.id.rbCustomer)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        // Sign Up Button Click
        btnSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val userId = etUserId.text.toString().trim()

            // Get selected role
            val selectedRoleId = radioGroup.checkedRadioButtonId
            val role = when (selectedRoleId) {
                R.id.rbAdmin -> "Admin"
                R.id.rbCustomer -> "Customers"
                else -> ""
            }

            // Validate inputs
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && userId.isNotEmpty() && role.isNotEmpty()) {
                val user = mapOf(
                    "name" to name,
                    "email" to email,
                    "password" to password,
                    "userId" to userId
                )

                // Save to Firebase under Users -> Admin or Customers -> userId
                val database = FirebaseDatabase.getInstance().reference
                database.child("Users").child(role).child(userId).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show()

                        // Clear fields
                        etName.text.clear()
                        etEmail.text.clear()
                        etPassword.text.clear()
                        etUserId.text.clear()
                        radioGroup.clearCheck()

                        // Move to SignIn
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Signup Failed. Try Again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "All fields and role selection are required!", Toast.LENGTH_SHORT).show()
            }
        }

        // Already registered? Go to sign in
        tvAlreadyRegistered.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}
