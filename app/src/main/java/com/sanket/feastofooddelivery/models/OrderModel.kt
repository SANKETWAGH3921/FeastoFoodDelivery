package com.sanket.feastofooddelivery.models



data class OrderModel(
    val orderId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val quantity: Int? = null,
    val price: Int? = null,
    val imageResId: Int? = null
)
