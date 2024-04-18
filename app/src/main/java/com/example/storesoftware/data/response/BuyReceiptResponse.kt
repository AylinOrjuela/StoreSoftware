package com.example.storesoftware.data.response

import com.example.storesoftware.domain.model.BuyReceipt

data class BuyReceiptResponse (
    var amount:String = "",
    var date:String = "",
    var description:String = "",
    var id:String = "",
    var units: String = "",
    var userId:String = ""
){
    fun toDomain():BuyReceipt{
        return BuyReceipt(
            idReceipt = id,
            userId = userId,
            date = date,
            description = description,
            units = units,
            amount = amount
        )
    }
}