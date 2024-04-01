package com.example.storesoftware.data.response

import com.example.storesoftware.domain.model.Product

data class ProductResponse(
    val id:String = "",
    val imageUrl: String = "",
    val name: String = "",
    val description: String = "",
    val price: String = ""
){
    fun toDomain(): Product {
        return Product(
            id = id,
            imageUrl = imageUrl,
            name = name,
            description = description,
            price = price
        )
    }
}