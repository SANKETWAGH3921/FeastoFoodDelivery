package com.sanket.feastofooddelivery.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.models.OrderModel

class OrderAdapter(private val orderList: List<OrderModel>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.orderImage)
        val name: TextView = itemView.findViewById(R.id.tvItemName)
        val description: TextView = itemView.findViewById(R.id.tvStatus)
        val quantity: TextView = itemView.findViewById(R.id.tvQuantity)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val orderId: TextView = itemView.findViewById(R.id.tvUserId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]

        holder.name.text = "Item: ${order.name}"
        holder.description.text = "Desc: ${order.description}"
        holder.quantity.text = "Qty: ${order.quantity}"
        holder.price.text = "Price: ₹${order.price}"
        holder.orderId.text = "Order ID: ${order.orderId}"


        order.imageResId?.let {
            holder.image.setImageResource(it)
        } ?: holder.image.setImageResource(R.drawable.ic_launcher_background)
    }

    override fun getItemCount(): Int = orderList.size
}
