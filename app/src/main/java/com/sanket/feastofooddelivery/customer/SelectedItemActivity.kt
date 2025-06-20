package com.sanket.feastofooddelivery.customer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.activities.CustomerActivity
import java.text.SimpleDateFormat
import java.util.*

class SelectedItemActivity : AppCompatActivity() {

    private lateinit var itemImage: ImageView
    private lateinit var itemName: TextView
    private lateinit var itemPrice: TextView
    private lateinit var itemDescription: TextView
    private lateinit var quantityText: TextView
    private lateinit var increaseBtn: Button
    private lateinit var decreaseBtn: Button
    private lateinit var addToCartBtn: Button
    private lateinit var buyNowBtn: Button

    private var quantity = 1
    private var unitPrice = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_item)

        // Initialize views
        itemImage = findViewById(R.id.selected_image)
        itemName = findViewById(R.id.selected_name)
        itemPrice = findViewById(R.id.selected_price)
        itemDescription = findViewById(R.id.selected_description)
        quantityText = findViewById(R.id.quantityText)
        increaseBtn = findViewById(R.id.increaseBtn)
        decreaseBtn = findViewById(R.id.decreaseBtn)
        addToCartBtn = findViewById(R.id.addtocart)
        buyNowBtn = findViewById(R.id.btnbuynow)

        // Get data from Intent
        val name = intent.getStringExtra("itemName") ?: "No Name"
        val description = intent.getStringExtra("description") ?: "No Description"
        val price = intent.getStringExtra("price") ?: "0"
        val imageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_background)

        unitPrice = price.replace("₹", "").trim().toIntOrNull() ?: 0

        // Set views
        itemImage.setImageResource(imageResId)
        itemName.text = name
        itemDescription.text = description
        updatePriceUI()

        increaseBtn.setOnClickListener {
            quantity++
            updatePriceUI()
        }

        decreaseBtn.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updatePriceUI()
            }
        }

        // ✅ Add to Cart with Notification
        addToCartBtn.setOnClickListener {
            val cartItemId = UUID.randomUUID().toString()
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val cartData = mapOf(
                "cartItemId" to cartItemId,
                "name" to name,
                "description" to description,
                "imageResId" to imageResId,
                "unitPrice" to unitPrice,
                "quantity" to quantity,
                "totalPrice" to unitPrice * quantity
            )

            val cartRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Customers")
                .child("Cart")
                .child(cartItemId)

            val notificationData = mapOf(
                "messageId" to UUID.randomUUID().toString(),
                "message" to "🛒 '$name' added to cart.",
                "timestamp" to timestamp
            )

            val notificationRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Customers")
                .child("Notifications")
                .push()

            cartRef.setValue(cartData)
                .addOnSuccessListener {
                    notificationRef.setValue(notificationData)
                    Toast.makeText(this, "'$name' added to cart!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, CustomerActivity::class.java)
                    intent.putExtra("goToHome", true)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                }
        }

        // ✅ Buy Now with Notification
        buyNowBtn.setOnClickListener {
            val orderId = UUID.randomUUID().toString()
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val orderData = mapOf(
                "orderId" to orderId,
                "name" to name,
                "description" to description,
                "imageResId" to imageResId,
                "price" to unitPrice * quantity,
                "quantity" to quantity,
                "timestamp" to timestamp
            )

            val ordersRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Admin")
                .child("Orders")
                .child(orderId)

            val notificationData = mapOf(
                "messageId" to UUID.randomUUID().toString(),
                "message" to "✅ Order placed for '$name'.",
                "timestamp" to timestamp
            )

            val notificationRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child("Customers")
                .child("Notifications")
                .push()

            ordersRef.setValue(orderData)
                .addOnSuccessListener {
                    notificationRef.setValue(notificationData)
                    Toast.makeText(this, "Order placed for '$name'!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, CustomerActivity::class.java)
                    intent.putExtra("goToHome", true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to place order", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updatePriceUI() {
        quantityText.text = quantity.toString()
        itemPrice.text = "₹${unitPrice * quantity}"
    }
}
