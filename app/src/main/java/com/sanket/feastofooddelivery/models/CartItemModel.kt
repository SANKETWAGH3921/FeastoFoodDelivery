package com.sanket.feastofooddelivery.models



data class CartItemModel(
    val cartItemId: String = "",
    val name: String = "",
    val description: String = "",
    val imageResId: Int = 0,
    val unitPrice: Int = 0,
    val quantity: Int = 1,
    val totalPrice: Int = 0
)
