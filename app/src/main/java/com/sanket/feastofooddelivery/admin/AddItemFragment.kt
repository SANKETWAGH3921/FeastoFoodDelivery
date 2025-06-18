package com.sanket.feastofooddelivery.admin

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.sanket.feastofooddelivery.R
import java.util.Calendar
import java.util.UUID

class AddItemFragment : Fragment() {

    private lateinit var spinnerCategory: Spinner
    private lateinit var etItemName: TextInputEditText
    private lateinit var etPrice: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etDate: TextInputEditText
    private lateinit var uploadButton: Button

    private var imageUri: Uri? = null // Optional if you plan to add image later
    private val categoryList = listOf("Pizza", "Burger", "Cake", "Dessert", "Veg", "Non-Veg")

    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        etItemName = view.findViewById(R.id.etItemName)
        etPrice = view.findViewById(R.id.etPrice)
        etDescription = view.findViewById(R.id.etDescription)
        etDate = view.findViewById(R.id.etDate)
        uploadButton = view.findViewById(R.id.uploadButton)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        etDate.setOnClickListener { showDatePicker() }
        uploadButton.setOnClickListener { uploadItem() }

        return view
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                etDate.setText("$day/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun uploadItem() {
        val name = etItemName.text.toString().trim()
        val price = etPrice.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val date = etDate.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()

        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val itemId = UUID.randomUUID().toString()
        val itemData = mapOf(
            "itemId" to itemId,
            "itemName" to name,
            "price" to price,
            "description" to description,
            "itemDate" to date,
            "category" to category
        )

        val databaseRef = FirebaseDatabase.getInstance().reference
            .child("Users").child("Admin").child("Items").child(category).child(itemId)

        databaseRef.setValue(itemData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Item uploaded successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        etItemName.setText("")
        etPrice.setText("")
        etDescription.setText("")
        etDate.setText("")
        spinnerCategory.setSelection(0)
    }
}