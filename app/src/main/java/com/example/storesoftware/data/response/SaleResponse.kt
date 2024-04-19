package com.example.storesoftware.data.response

import com.example.storesoftware.domain.model.Sale

data class SaleResponse (
    var id:String = "",
    var name:String = "",
    var date:String = "",
    var description:String = "",
    var amount: Int = 0,
    var totalPrice:String = ""){

fun toDomain(): Sale{
    return Sale(
        id = id,
        name = name,
        date = date,
        description = description,
        amount = amount,
        totalPrice = totalPrice
    )
}
}