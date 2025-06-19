package com.sanket.feastofooddelivery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.models.ItemModel

class ItemAdapter(
    private val itemList: List<ItemModel>,
    private val onItemClick: ((ItemModel, Int) -> Unit)? = null // Optional click listener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val tvItemName: TextView = itemView.findViewById(R.id.item_name)
        val tvPrice: TextView = itemView.findViewById(R.id.item_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        val context: Context = holder.itemView.context

        holder.tvItemName.text = item.itemName
        holder.tvPrice.text = "₹${item.price}"

        // Load image from drawable using itemName
        val imageResId = context.resources.getIdentifier(
            item.itemName.lowercase(), "drawable", context.packageName
        )

        if (imageResId != 0) {
            holder.itemImage.setImageResource(imageResId)
        } else {
            holder.itemImage.setImageResource(R.drawable.ic_launcher_background)
            Toast.makeText(context, "Image not found for ${item.itemName}", Toast.LENGTH_SHORT).show()
        }

        // Only handle click if listener is provided
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item, imageResId)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
