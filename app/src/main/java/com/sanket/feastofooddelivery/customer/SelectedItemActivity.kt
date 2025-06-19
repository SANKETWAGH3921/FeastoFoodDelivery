package com.sanket.feastofooddelivery.customer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.sanket.feastofooddelivery.R

class SelectedItemActivity : AppCompatActivity() {

    private lateinit var itemImage: ImageView
    private lateinit var itemName: TextView
    private lateinit var itemPrice: TextView
    private lateinit var itemDescription: TextView
    private lateinit var quantityText: TextView
    private lateinit var increaseBtn: Button
    private lateinit var decreaseBtn: Button
    private lateinit var addToCartBtn: Button

    private var quantity = 1
    private var unitPrice = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_item)

        // Initialize views with matching IDs from your layout
        itemImage = findViewById(R.id.selected_image)
        itemName = findViewById(R.id.selected_name)
        itemPrice = findViewById(R.id.selected_price)
        itemDescription = findViewById(R.id.selected_description)
        quantityText = findViewById(R.id.quantityText)
        increaseBtn = findViewById(R.id.increaseBtn)
        decreaseBtn = findViewById(R.id.decreaseBtn)
        addToCartBtn = findViewById(R.id.addtocart)

        // Get data from intent
        val name = intent.getStringExtra("itemName") ?: "No Name"
        val description = intent.getStringExtra("description") ?: "No Description"
        val price = intent.getStringExtra("price") ?: "0"
        val imageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_background)

        unitPrice = price.replace("₹", "").trim().toIntOrNull() ?: 0

        // Set data
        itemImage.setImageResource(imageResId)
        itemName.text = name
        itemDescription.text = description
        updatePriceUI()

        // Button listeners
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

        addToCartBtn.setOnClickListener {
            Toast.makeText(
                this,
                "$quantity x $name added to cart. Total: ₹${unitPrice * quantity}",
                Toast.LENGTH_SHORT
            ).show()
            // You can add Firebase cart code here if needed.
        }
    }

    private fun updatePriceUI() {
        quantityText.text = quantity.toString()
        itemPrice.text = "₹${unitPrice * quantity}"
    }
}
