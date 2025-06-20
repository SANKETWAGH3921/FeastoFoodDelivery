package com.sanket.feastofooddelivery.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.models.CartItemModel


class CartAdapter(private val cartList: List<CartItemModel>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val nameText: TextView = itemView.findViewById(R.id.item_name)
        val qtyText: TextView = itemView.findViewById(R.id.item_qty)
        val totalText: TextView = itemView.findViewById(R.id.item_total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.nameText.text = item.name
        holder.qtyText.text = "Qty: ${item.quantity}"
        holder.totalText.text = "₹${item.totalPrice}"
    }

    override fun getItemCount(): Int = cartList.size
}
